package com.example.dp.ui.home

import androidx.navigation.fragment.findNavController
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.ErrorCodes
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.appComponent
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel: HomeViewModel by appViewModels()

    override fun initUI() {
        binding.btnSignOut.setOnClickListener {
            binding.root.context.appComponent.prefUtils.userID = PrefUtils.DEFAULT_USER_ID_VALUE
            findNavController().navigate(
                HomeFragmentDirections.actionFHomeToFAuth()
            )
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.userInfo,
            useLoadingData = true,
            onSuccess = { userModel ->
                binding.textHome.text = userModel.displayName
            },
            onFailure = { errorCode, messageID, throwable ->
                binding.textHome.text = when (errorCode) {
                    ErrorCodes.STATE_NO_DATA -> "no data found"
                    else                     -> "unknown error"
                }
            }
        )
    }
}