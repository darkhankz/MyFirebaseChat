package com.example.myfirebasechat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfirebasechat.data.Chat
import com.example.myfirebasechat.data.User
import com.example.myfirebasechat.model.repository.ChatRepository
import com.example.myfirebasechat.model.repository.ChatRepositoryImpl

class ChatViewModel {
    private val mChatRepository: ChatRepository = ChatRepositoryImpl()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
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
            },
            errorCallback = { errorMessage ->
                _error.postValue(errorMessage)
            }
        )
    }
}

