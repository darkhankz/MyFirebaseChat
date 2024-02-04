package com.example.myfirebasechat.model.repository

import com.example.myfirebasechat.data.User

interface UsersRepository {
    fun getUsersList(callback: (ArrayList<User>) -> Unit, errorCallback: (String) -> Unit)
}