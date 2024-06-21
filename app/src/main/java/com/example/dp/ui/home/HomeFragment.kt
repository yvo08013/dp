package com.example.dp.ui.home

import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.appViewModels
import com.example.dp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by appViewModels()
}