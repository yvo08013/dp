package com.example.dp.ui.profile

import androidx.navigation.fragment.findNavController
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.appComponent
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel: ProfileViewModel by appViewModels()

    override fun initUI() {
        binding.btnLeave.setOnClickListener {
            binding.root.context.appComponent.prefUtils.userID = PrefUtils.DEFAULT_USER_ID_VALUE
            findNavController().navigate(
                ProfileFragmentDirections.actionFProfileToFAuth()
            )
        }
        binding.btnDelete.setOnClickListener {
            viewModel.deleteUser()
            binding.root.context.appComponent.prefUtils.userID = PrefUtils.DEFAULT_USER_ID_VALUE
            findNavController().navigate(
                ProfileFragmentDirections.actionFProfileToFAuth()
            )
        }
        binding.groupValue.setOnClickListener {
            if (viewModel.userGroupID != null) {
                findNavController().navigate(
                    ProfileFragmentDirections.actionFProfileToFGroup(viewModel.userGroupID!!)
                )
            } else {
                findNavController().navigate(
                    ProfileFragmentDirections.actionFProfileToFGroupList()
                )
            }
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.userInfo,
            useLoadingData = true,
            onSuccess = { userModel ->
                binding.absenceValue.setOnClickListener {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionFProfileToFUserAbsence(
                            userModel.ID,
                            userModel.name,
                        )
                    )
                }
                viewModel.userGroupID = userModel.group?.ID
                binding.nameValue.text = userModel.name
                binding.absenceValue.text = userModel.absenceCount
                binding.statusValue.text = userModel.status.displayName
                binding.groupValue.text = userModel.group?.name ?: "-"
            }
        )
    }
}