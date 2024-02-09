package com.example.myfirebasechat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirebasechat.data.User
import com.example.myfirebasechat.model.repository.UsersRepository
import com.example.myfirebasechat.model.repository.UsersRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val mUsersRepository: UsersRepository) :
    ViewModel() {

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