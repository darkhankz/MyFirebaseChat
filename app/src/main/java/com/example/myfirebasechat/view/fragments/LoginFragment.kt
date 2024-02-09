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
import com.example.myfirebasechat.databinding.FragmentLoginBinding
import com.example.myfirebasechat.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val mBinding get() = _binding!!

    private val mSignUpViewModel: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = mBinding.root
        navigateToSignUp()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButtonClick()
        observerLoginResult()
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

    private fun observerLoginResult() {
        mSignUpViewModel.loginResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                clearFields()
                findNavController().navigate(R.id.action_loginFragment_to_usersFragment)
            } else {
                showToast("Email or password invalid")
            }
        }
    }

    private fun navigateToSignUp() {
        mBinding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun clearFields() {
        mBinding.etEmail.setText("")
        mBinding.etPassword.setText("")
    }
}