package com.example.dp.ui.sign_in

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.dp.R
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.ErrorCodes
import com.example.dp.core.utils.appComponent
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentSignInBinding


class SignInFragment : BaseFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {

    private val viewModel: SignInViewModel by appViewModels()


    override fun initUI() {
        binding.passwordInput.doOnTextChanged { _, _, _, _ ->
            binding.btnConfirm.isEnabled = isValidInput()
        }
        binding.loginInput.doOnTextChanged { _, _, _, _ ->
            binding.btnConfirm.isEnabled = isValidInput()
        }
        binding.btnConfirm.setOnClickListener {
            viewModel.onConfirmButtonClicked(
                name = binding.loginInput.text.toString(),
                password = binding.passwordInput.text.toString()
            )
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.signInState,
            useLoadingData = true,
            onSuccess = { user ->
                binding.root.context.appComponent.prefUtils.userID = user.id!!
                findNavController().navigate(
                    SignInFragmentDirections.actionFSignInToFHome()
                )
            },
            onFailure = { errorCode, messageID, throwable ->
                when (errorCode) {
                    ErrorCodes.STATE_NO_DATA -> {
                        Toast.makeText(
                            binding.root.context,
                            R.string.error_incorrect_login_data,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }

    private fun isValidInput() =
        binding.passwordInput.text.length > 5 &&
        binding.loginInput.text.length > 5
}