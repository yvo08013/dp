package com.example.dp.ui.home

import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.ErrorCodes
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel: HomeViewModel by appViewModels()

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.name,
            useLoadingData = true,
            onSuccess = { homeModel ->
                binding.textHome.text = homeModel.title
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