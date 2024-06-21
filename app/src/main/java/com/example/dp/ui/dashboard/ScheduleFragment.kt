package com.example.dp.ui.dashboard

import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.appViewModels
import com.example.dp.databinding.FragmentScheduleBinding

class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(
    FragmentScheduleBinding::inflate
) {
    private val viewModel: ScheduleViewModel by appViewModels()
}