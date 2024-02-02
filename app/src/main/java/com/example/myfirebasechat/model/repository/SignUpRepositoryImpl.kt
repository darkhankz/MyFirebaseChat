package com.example.myfirebasechat.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfirebasechat.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpRepositoryImpl : SignUpRepository {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var databaseReference: DatabaseReference

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

                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val userObject = User(userId, userName, "")

                    // Пример: добавление пользователя в базу данных
                    databaseReference.setValue(userObject)
                        .addOnCompleteListener { databaseTask ->
                            resultLiveData.value = databaseTask.isSuccessful
                        }
                } else {
                    resultLiveData.value = false
                }
            }

        return resultLiveData
    }
}
