package com.example.myfirebasechat.model.repository

import com.example.myfirebasechat.data.Chat
import com.example.myfirebasechat.data.User

interface ChatRepository {
    fun getUserData(userId: String, callback: (User?) -> Unit, errorCallback: (String) -> Unit)

    fun readChatMessages(
        senderId: String,
        receiverId: String,
        callback: (List<Chat>) -> Unit,
        errorCallback: (String) -> Unit
    )
}