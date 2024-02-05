package com.example.myfirebasechat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirebasechat.databinding.FragmentUsersBinding
import com.example.myfirebasechat.view.adapter.UserAdapter
import com.example.myfirebasechat.viewmodel.UsersViewModel

class UsersFragment : Fragment() {
    private var _binding: FragmentUsersBinding? = null
    private val mBinding get() = _binding!!

    private val mUsersViewModel: UsersViewModel = UsersViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        val view = mBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUserRecyclerView()
        initObservers()
        mUsersViewModel.fetchUsersList()


    }

    private fun setupUserRecyclerView() {
        mBinding.userRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }


    private fun initObservers() {
        mUsersViewModel.userList.observe(viewLifecycleOwner) { userList ->
            val userAdapter = context?.let { UserAdapter(it, userList) }
            mBinding.userRecyclerView.adapter = userAdapter
        }

        mUsersViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}