package com.example.myfirebasechat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirebasechat.databinding.ActivityUsersBinding
import com.example.myfirebasechat.view.adapter.UserAdapter
import com.example.myfirebasechat.viewmodel.UsersViewModel

//class UsersActivity : AppCompatActivity() {
//    private lateinit var mBinding: ActivityUsersBinding
//    private val mUsersViewModel: UsersViewModel = UsersViewModel()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mBinding = ActivityUsersBinding.inflate(layoutInflater)
//        val view = mBinding.root
//        setContentView(view)
//
//        setupUserRecyclerView()
//        initObservers()
//        mUsersViewModel.fetchUsersList()
//    }
//
//    private fun setupUserRecyclerView() {
//        mBinding.userRecyclerView.layoutManager =
//            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//    }
//
//    private fun initObservers() {
//        mUsersViewModel.userList.observe(this) { userList ->
//            val userAdapter = UserAdapter(this@UsersActivity, userList)
//            mBinding.userRecyclerView.adapter = userAdapter
//        }
//
//        mUsersViewModel.error.observe(this) { errorMessage ->
//            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//}