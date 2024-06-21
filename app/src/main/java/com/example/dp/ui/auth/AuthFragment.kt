package com.example.dp.ui.auth

import androidx.navigation.fragment.findNavController
import com.example.dp.core.ui.BaseFragment
import com.example.dp.databinding.FragmentAuthBinding


class AuthFragment : BaseFragment<FragmentAuthBinding>(
    FragmentAuthBinding::inflate
) {
    override fun initUI() {
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(
                AuthFragmentDirections.actionFAuthToFSignIn()
            )
        }
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(
                AuthFragmentDirections.actionFAuthToFSignUp()
            )
        }
    }
}