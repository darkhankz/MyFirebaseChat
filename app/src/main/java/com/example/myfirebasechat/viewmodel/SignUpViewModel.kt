package com.example.myfirebasechat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfirebasechat.model.repository.SignUpRepository
import com.example.myfirebasechat.model.repository.SignUpRepositoryImpl

class SignUpViewModel {
    private val mSignUpRepository: SignUpRepository = SignUpRepositoryImpl()

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean>
        get() = _registrationResult

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult

    fun registerUser(userName: String, email: String, password: String) {
        mSignUpRepository.registerUser(userName, email, password)
            .observeForever { isSuccess ->
                _registrationResult.value = isSuccess
            }
    }

    fun loginUser(email: String, password: String) {
        mSignUpRepository.loginUser(email, password)
            .observeForever { isSuccess ->
                _loginResult.value = isSuccess
            }
    }
}
