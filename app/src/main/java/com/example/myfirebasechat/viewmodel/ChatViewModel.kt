package com.example.myfirebasechat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirebasechat.data.Chat
import com.example.myfirebasechat.data.User
import com.example.myfirebasechat.model.repository.ChatRepository
import com.example.myfirebasechat.model.repository.ChatRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val mChatRepository: ChatRepository) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _chatList = MutableLiveData<ArrayList<Chat>>()
    val chatList: LiveData<ArrayList<Chat>>
        get() = _chatList

    fun getUserData(userId: String) {
        mChatRepository.getUserData(
            userId = userId,
            callback = { user ->
                _user.postValue(user)
            },
            errorCallback = { errorMessage ->
                _error.postValue(errorMessage)
            }
        )
    }

    fun readChatMessages(senderId: String, receiverId: String) {
        mChatRepository.readChatMessages(
            senderId = senderId,
            receiverId = receiverId,
            callback = { chatList ->
                _chatList.postValue(ArrayList(chatList))
                Log.d("ChatViewModel", "Chat messages loaded successfully: $chatList")

            },
            errorCallback = { errorMessage ->
                _error.postValue(errorMessage)
                Log.e("ChatViewModel", "Error loading chat messages: $errorMessage")
            }
        )
    }
}

