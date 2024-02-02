package com.example.myfirebasechat.model.repository

import androidx.lifecycle.LiveData

interface SignUpRepository {
    fun registerUser(userName:String,email:String,password:String): LiveData<Boolean>
    fun loginUser(email: String, password: String): LiveData<Boolean>
}