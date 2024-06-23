package com.example.dp.ui.user_absence

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.ui.adapter.AppListAdapter
import com.example.dp.core.ui.adapter.appListAdapter
import com.example.dp.core.utils.ErrorCodes
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentUserAbsenceBinding

class UserAbsenceFragment : BaseFragment<FragmentUserAbsenceBinding>(
    FragmentUserAbsenceBinding::inflate
) {
    private val viewModel: UserAbsenceViewModel by appViewModels()

    private val args: UserAbsenceFragmentArgs by navArgs()

    private val onItemClickListener: (UserAbsenceUIModel) -> Unit = { absence ->
        findNavController().navigate(
            UserAbsenceFragmentDirections.actionFUserAbsenceToFSubject(absence.subjectID)
        )
    }

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        absenceAdapterDelegate(onItemClickListener),
        absenceDividerAdapterDelegate()
    )

    override fun initUI() {
        viewModel.userID = args.userID
        binding.userName.text = args.userName

        with(binding.rvAbsenceList) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.absenceList,
            useLoadingData = true,
            onSuccess = { absenceList ->
                binding.userName.isVisible = true
                binding.userNameTitle.isVisible = true
                binding.rvAbsenceList.isVisible = true
                binding.noAbsenceTitle.isVisible = false
                recyclerAdapter.submitList(absenceList)
            },
            onFailure = { errorCode, messageID, throwable ->
                if (errorCode == ErrorCodes.STATE_NO_DATA) {
                    binding.userName.isVisible = false
                    binding.userNameTitle.isVisible = false
                    binding.rvAbsenceList.isVisible = false
                    binding.noAbsenceTitle.isVisible = true
                }
            }
        )
    }
}