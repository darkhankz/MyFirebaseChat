package com.example.myfirebasechat.model.repository

import com.example.myfirebasechat.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val databaseReference: DatabaseReference,
    private val auth: FirebaseAuth
) : UsersRepository {
    override fun getUsersList(
        callback: (ArrayList<User>) -> Unit,
        errorCallback: (String) -> Unit
    ) {
        val firebaseUser: FirebaseUser? = auth.currentUser

        if (firebaseUser != null) {
            val databaseReference: DatabaseReference = databaseReference

            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    errorCallback(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val userList = mutableListOf<User>()

                    for (dataSnapShot: DataSnapshot in snapshot.children) {
                        val user = dataSnapShot.getValue(User::class.java)

                        if (user != null && user.userId != firebaseUser.uid) {
                            userList.add(user)
                        }
                    }

                    callback(ArrayList(userList))
                }
            })
        } else {
            errorCallback("User not authenticated")
        }
    }
}

