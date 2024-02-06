package com.example.myfirebasechat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfirebasechat.data.User
import com.example.myfirebasechat.model.repository.UsersRepository
import com.example.myfirebasechat.model.repository.UsersRepositoryImpl

class UsersViewModel {
    private val mUsersRepository: UsersRepository = UsersRepositoryImpl()

    private val _userList = MutableLiveData<ArrayList<User>>()
    val userList: LiveData<ArrayList<User>>
        get() = _userList


    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun fetchUsersList() {
        mUsersRepository.getUsersList(
            callback = { userList ->
                _userList.postValue(userList)
            },
            errorCallback = { errorMessage ->
                _error.postValue(errorMessage)
            }
        )
    }
}