package com.example.dp.ui.group_list

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.ui.adapter.AppListAdapter
import com.example.dp.core.ui.adapter.appListAdapter
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentGroupListBinding

class GroupListFragment : BaseFragment<FragmentGroupListBinding>(
    FragmentGroupListBinding::inflate
) {
    private val viewModel: GroupListViewModel by appViewModels()

    private val onItemClickListener: (UiModel.GroupUIModel) -> Unit = { group ->
        findNavController().navigate(
            GroupListFragmentDirections.actionFGroupListToFGroup(group.ID)
        )
    }

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        groupAdapterDelegate(onItemClickListener)
    )

    override fun initUI() {
        with(binding.rvGroupList) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnCreate.setOnClickListener {
            findNavController().navigate(
                GroupListFragmentDirections.actionFGroupListToFCreateGroup()
            )
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.groupList,
            useLoadingData = true,
            onSuccess = { uiModel ->
                binding.btnCreate.isVisible = uiModel.canCreateGroup
                recyclerAdapter.submitList(uiModel.groups)
            }
        )
    }
}