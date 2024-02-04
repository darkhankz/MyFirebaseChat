package com.example.myfirebasechat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfirebasechat.data.Chat
import com.example.myfirebasechat.databinding.ActivityChatBinding
import com.example.myfirebasechat.view.adapter.ChatAdapter
import com.example.myfirebasechat.viewmodel.ChatViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.myfirebasechat.R

class ChatActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityChatBinding
    private val mChatViewModel: ChatViewModel = ChatViewModel()

    private var firebaseUser: FirebaseUser? = null
    private var reference: DatabaseReference? = null
    private var topic = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChatBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        val intent = intent
        val userId = intent.getStringExtra("userId")

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        setupUserRecyclerView()
        onBackPressedListener()
        initObservers()
        mChatViewModel.getUserData(userId)
        sendMessageButtonClick(userId)
        mChatViewModel.readChatMessages(firebaseUser!!.uid, userId)

    }

    private fun sendMessageButtonClick(userId: String?) {
        mBinding.btnSendMessage.setOnClickListener {
            val message: String = mBinding.etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "message is empty", Toast.LENGTH_SHORT).show()
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

    private fun initObservers() {
        mChatViewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this@ChatActivity, errorMessage, Toast.LENGTH_SHORT).show()
        }

        mChatViewModel.chatList.observe(this) { chatList ->
            val chatAdapter = ChatAdapter(this@ChatActivity, chatList)
            mBinding.chatRecyclerView.adapter = chatAdapter
        }

        mChatViewModel.user.observe(this) { user ->
            mBinding.tvUserName.text = user.userName
            if (user.profileImage == "") {
                mBinding.imgProfile.setImageResource(R.drawable.profile_image)
            } else {
                Glide.with(this@ChatActivity).load(user.profileImage).into(mBinding.imgProfile)
            }
        }
    }

    private fun ChatActivity.onBackPressedListener() {
        mBinding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupUserRecyclerView() {
        mBinding.chatRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val chat = Chat(senderId, receiverId, message)
        reference.child("Chat").push().setValue(chat)
    }

}
