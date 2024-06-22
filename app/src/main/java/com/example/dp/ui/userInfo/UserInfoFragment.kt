package com.example.dp.ui.userInfo

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentUserInfoBinding

class UserInfoFragment : BaseFragment<FragmentUserInfoBinding>(
    FragmentUserInfoBinding::inflate
) {
    private val viewModel: UserInfoViewModel by appViewModels()

    private val args: UserInfoFragmentArgs by navArgs()

    override fun initUI() {
        viewModel.userID = args.userID
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.groupValue.setOnClickListener {
            if (viewModel.userGroupID != null) {
                findNavController().navigate(
                    UserInfoFragmentDirections.actionFUserInfoToFGroup(viewModel.userGroupID!!)
                )
            } else {
                //TODO umplement group list screen
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
                        UserInfoFragmentDirections.actionFUserInfoToFUserAbsence(
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