package com.example.myfirebasechat.model.repository

import com.example.myfirebasechat.data.Chat
import com.example.myfirebasechat.data.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatRepositoryImpl : ChatRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")


    override fun getUserData(
        userId: String,
        callback: (User?) -> Unit,
        errorCallback: (String) -> Unit
    ) {
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                callback(user)
            }

            override fun onCancelled(error: DatabaseError) {
                errorCallback(error.message)
            }
        })
    }


    override fun readChatMessages(
        senderId: String,
        receiverId: String,
        callback: (List<Chat>) -> Unit,
        errorCallback: (String) -> Unit
    ) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatList = mutableListOf<Chat>()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)

                    if (chat != null) {
                        if ((chat.senderId == senderId && chat.receiverId == receiverId) ||
                            (chat.senderId == receiverId && chat.receiverId == senderId)
                        ) {
                            chatList.add(chat)
                        }
                    }
                }
                callback(chatList)
            }

            override fun onCancelled(error: DatabaseError) {
                errorCallback(error.message)
            }
        })
    }

}