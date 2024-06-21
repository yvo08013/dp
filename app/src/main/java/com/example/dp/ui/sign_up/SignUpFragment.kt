package com.example.dp.ui.sign_up

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.dp.R
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.ErrorCodes
import com.example.dp.core.utils.appComponent
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {

    private val viewModel: SignUpViewModel by appViewModels()


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
                password = binding.passwordInput.text.toString(),
                userType = when {
                    binding.choiceUser.isChecked -> 1
                    else                         -> 2
                }
            )
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.signInState,
            useLoadingData = true,
            onSuccess = { userID ->
                binding.root.context.appComponent.prefUtils.userID = userID
                findNavController().navigate(
                    SignUpFragmentDirections.actionFSignUpToFHome()
                )
            },
            onFailure = { errorCode, messageID, throwable ->
                when (errorCode) {
                    ErrorCodes.STATE_LOCAL_UNKNOWN -> {
                        Toast.makeText(
                            binding.root.context,
                            R.string.error_user_exists,
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