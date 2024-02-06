package com.example.myfirebasechat.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfirebasechat.R
import com.example.myfirebasechat.data.Chat
import com.example.myfirebasechat.databinding.FragmentChatBinding
import com.example.myfirebasechat.view.adapter.ChatAdapter
import com.example.myfirebasechat.viewmodel.ChatViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val mBinding get() = _binding!!

    private val mChatViewModel: ChatViewModel = ChatViewModel()


    private var firebaseUser: FirebaseUser? = null
    private var reference: DatabaseReference? = null
    private var topic = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val view = mBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getString("userId")
        Log.d("userId", "Received user ID: $userId")

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        setupUserRecyclerView()
        onBackBtnListener()
        initObservers()
        mChatViewModel.getUserData(userId)
        sendMessageButtonClick(userId)
        mChatViewModel.readChatMessages(firebaseUser!!.uid, userId)
    }


    private fun setupUserRecyclerView() {
        mBinding.chatRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun onBackBtnListener() {
        mBinding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_chatFragment_to_usersFragment)
        }
    }

    private fun initObservers() {
        mChatViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }

        mChatViewModel.chatList.observe(viewLifecycleOwner) { chatList ->
            val chatAdapter = context?.let { ChatAdapter(it, chatList) }
            mBinding.chatRecyclerView.adapter = chatAdapter
        }

        mChatViewModel.user.observe(viewLifecycleOwner) { user ->
            mBinding.tvUserName.text = user.userName
            if (user.profileImage == "") {
                mBinding.imgProfile.setImageResource(R.drawable.profile_image)
            } else {
                context?.let { Glide.with(it).load(user.profileImage).into(mBinding.imgProfile) }
            }
        }
    }

    private fun sendMessageButtonClick(userId: String?) {
        mBinding.btnSendMessage.setOnClickListener {
            val message: String = mBinding.etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(context, "message is empty", Toast.LENGTH_SHORT).show()
                mBinding.etMessage.setText("")
            } else {
                if (userId != null) {
                    sendMessage(firebaseUser!!.uid, userId, message)
                }
                mBinding.etMessage.setText("")
                topic = "/topics/$userId"
            }

        }
    }

    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val chat = Chat(senderId, receiverId, message)
        reference.child("Chat").push().setValue(chat)
    }

}