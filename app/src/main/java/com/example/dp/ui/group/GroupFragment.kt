package com.example.dp.ui.group

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.ui.adapter.AppListAdapter
import com.example.dp.core.ui.adapter.appListAdapter
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentGroupBinding

class GroupFragment : BaseFragment<FragmentGroupBinding>(
    FragmentGroupBinding::inflate
) {
    private val viewModel: GroupViewModel by appViewModels()

    private val args: GroupFragmentArgs by navArgs()

    private val onItemClickListener: (GroupUIModel.UserUIModel) -> Unit = { userModel ->
        //TODO add navigation to user screen
    }

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        groupMemberAdapterDelegate(onItemClickListener)
    )

    override fun initUI() {
        viewModel.groupID = args.groupID

        with(binding.rvGroupMembers) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.groupInfo,
            useLoadingData = true,
            onSuccess = { groupModel ->
                binding.groupName.text = groupModel.name
                binding.adminNameValue.text = groupModel.admin.name
                val joinInputVisibility = groupModel.userGroupStatus == GroupUIModel.UserGroupStatus.GUEST
                binding.passwordInput.isVisible = joinInputVisibility
                binding.btnJoin.isVisible = joinInputVisibility
                recyclerAdapter.submitList(groupModel.members)
            }
        )
    }
}