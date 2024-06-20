package com.example.dp.ui.notifications

import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.ErrorCodes
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentNotificationsBinding

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(
    FragmentNotificationsBinding::inflate
) {

    private val viewModel: NotificationsViewModel by appViewModels()

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.name,
            useLoadingData = true,
            onSuccess = { homeModel ->
                binding.textNotifications.text = homeModel.title
            },
            onFailure = { errorCode, messageID, throwable ->
                binding.textNotifications.text = when (errorCode) {
                    ErrorCodes.STATE_NO_DATA -> "no data found"
                    else                     -> "unknown error"
                }
            }
        )
    }
}