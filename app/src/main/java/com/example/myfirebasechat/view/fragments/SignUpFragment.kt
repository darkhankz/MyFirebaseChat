package com.example.myfirebasechat.view.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myfirebasechat.R
import com.example.myfirebasechat.databinding.FragmentSignUpBinding
import com.example.myfirebasechat.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val mBinding get() = _binding!!

    private val mSignUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = mBinding.root
        navigateToLogin()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerRegistrationResult()
        signUpButtonClick()
    }

    private fun observerRegistrationResult() {
        mSignUpViewModel.registrationResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().navigate(R.id.action_signUpFragment_to_usersFragment)

            } else {
                Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT)
                    .show()

            }

        }
    }

    private fun signUpButtonClick() {
        mBinding.apply {
            mBinding.btnSignUp.setOnClickListener {
                val userName = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(context, "username is required", Toast.LENGTH_SHORT)
                        .show()
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(context, "email is required", Toast.LENGTH_SHORT)
                        .show()
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(context, "password is required", Toast.LENGTH_SHORT)
                        .show()
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(
                        context,
                        "confirm password is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                if (password != confirmPassword) {
                    Toast.makeText(context, "password not match", Toast.LENGTH_SHORT)
                        .show()
                }
                mSignUpViewModel.registerUser(userName, email, password)

            }

        }
    }

    private fun navigateToLogin() {
        mBinding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

}