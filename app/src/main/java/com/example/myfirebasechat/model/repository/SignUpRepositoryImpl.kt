package com.example.myfirebasechat.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfirebasechat.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val databaseReference: DatabaseReference,
    private val auth: FirebaseAuth
) : SignUpRepository {

    override fun registerUser(
        userName: String,
        email: String,
        password: String
    ): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid

                    val userObject = User(userId, userName, "")

                    databaseReference.child(userId).setValue(userObject)
                        .addOnCompleteListener { databaseTask ->
                            resultLiveData.value = databaseTask.isSuccessful
                        }
                } else {
                    resultLiveData.value = false
                }
            }

        return resultLiveData
    }

    override fun loginUser(email: String, password: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                resultLiveData.value = task.isSuccessful
            }
        return resultLiveData
    }
}
