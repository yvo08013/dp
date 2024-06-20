package com.example.dp.ui.dashboard

import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.ErrorCodes
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
) {

    private val viewModel: DashboardViewModel by appViewModels()

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.name,
            useLoadingData = true,
            onSuccess = { homeModel ->
                binding.textDashboard.text = homeModel.title
            },
            onFailure = { errorCode, messageID, throwable ->
                binding.textDashboard.text = when (errorCode) {
                    ErrorCodes.STATE_NO_DATA -> "no data found"
                    else                     -> "unknown error"
                }
            }
        )
    }
}