package com.example.myfirebasechat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.myfirebasechat.databinding.ActivitySignUpBinding
import com.example.myfirebasechat.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private val mSignUpViewModel: SignUpViewModel = SignUpViewModel()
    private lateinit var mBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)
        observeRegistrationResult()
        signUpButtonClick()
    }

    private fun signUpButtonClick() {
        mBinding.apply {
            mBinding.btnSignUp.setOnClickListener {
                val userName = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(applicationContext, "username is required", Toast.LENGTH_SHORT)
                        .show()
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(applicationContext, "email is required", Toast.LENGTH_SHORT)
                        .show()
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(applicationContext, "password is required", Toast.LENGTH_SHORT)
                        .show()
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(
                        applicationContext,
                        "confirm password is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                if (password != confirmPassword) {
                    Toast.makeText(applicationContext, "password not match", Toast.LENGTH_SHORT)
                        .show()
                }
                mSignUpViewModel.registerUser(userName, email, password)

            }

        }
    }
    private fun observeRegistrationResult() {
        mSignUpViewModel.registrationResult.observe(this) { isSuccess ->
            if (isSuccess) {
                // Регистрация успешна, открываем HomeActivity
                val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@SignUpActivity, "Регистрация не удалась", Toast.LENGTH_SHORT)
                    .show()

            }

        }
    }
}