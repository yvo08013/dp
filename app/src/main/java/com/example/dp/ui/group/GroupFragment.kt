package com.example.dp.ui.group

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dp.R
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.ui.adapter.AppListAdapter
import com.example.dp.core.ui.adapter.appListAdapter
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observe
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentGroupBinding
import kotlinx.coroutines.flow.collectLatest

class GroupFragment : BaseFragment<FragmentGroupBinding>(
    FragmentGroupBinding::inflate
) {
    private val viewModel: GroupViewModel by appViewModels()

    private val args: GroupFragmentArgs by navArgs()

    private val onItemClickListener: (GroupUIModel.UserUIModel) -> Unit = { userModel ->
        findNavController().navigate(
            GroupFragmentDirections.actionFGroupToFUserInfo(userModel.ID)
        )
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

        binding.btnJoin.setOnClickListener {
            viewModel.joinToGroup(binding.passwordInput.text.toString())
        }

        binding.btnInviteUser.setOnClickListener {

        }
    }

    override fun subscribeUI() {
        observe(Lifecycle.State.STARTED) {
            viewModel.joinResponseState.collectLatest { isSuccess ->
                if (!isSuccess) {
                    Toast.makeText(
                        binding.root.context,
                        R.string.error_incorrect_password,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        observeState(
            dataFlow = viewModel.groupInfo,
            useLoadingData = true,
            onSuccess = { groupModel ->
                binding.groupName.text = groupModel.name
                binding.adminNameValue.text = groupModel.admin.name
                when (groupModel.userGroupStatus) {
                    GroupUIModel.UserGroupStatus.ADMIN -> {
                        binding.passwordInput.isVisible = false
                        binding.btnJoin.isVisible = false
                        binding.btnSchedule.isVisible = true
                        binding.btnInviteUser.isVisible = true
                    }

                    GroupUIModel.UserGroupStatus.GUEST_FREE -> {
                        binding.passwordInput.isVisible = true
                        binding.btnJoin.isVisible = true
                        binding.btnSchedule.isVisible = false
                        binding.btnInviteUser.isVisible = false
                    }

                    else -> {
                        binding.passwordInput.isVisible = false
                        binding.btnJoin.isVisible = false
                        binding.btnSchedule.isVisible = true
                        binding.btnInviteUser.isVisible = false
                    }
                }
                recyclerAdapter.submitList(groupModel.members)
            }
        )
    }
}