package com.example.myfirebasechat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.myfirebasechat.databinding.ActivityLoginBinding
import com.example.myfirebasechat.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private val mSignUpViewModel: SignUpViewModel = SignUpViewModel()
    private lateinit var mBinding: ActivityLoginBinding
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        if (firebaseUser != null) {
            navigateToUsersActivity()
        }
        loginButtonClick()
        observerLoginResult()
        navigateToSignUp()
    }

    private fun navigateToUsersActivity() {
        val intent = Intent(
            this@LoginActivity,
            UsersActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun navigateToSignUp() {
        mBinding.btnSignUp.setOnClickListener {
            val intent = Intent(
                this@LoginActivity,
                SignUpActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }

    private fun observerLoginResult() {
        mSignUpViewModel.loginResult.observe(this) { isSuccess ->
            if (isSuccess) {
                clearFields()
                startActivity(Intent(this@LoginActivity, UsersActivity::class.java))
                finish()
            } else {
                showToast("Email or password invalid")
            }
        }
    }

    private fun loginButtonClick() {
        mBinding.btnLogin.setOnClickListener {
            val email = mBinding.etEmail.text.toString()
            val password = mBinding.etPassword.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                showToast("Email and password are required")
            } else {
                mSignUpViewModel.loginUser(email, password)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun clearFields() {
        mBinding.etEmail.setText("")
        mBinding.etPassword.setText("")
    }

}
