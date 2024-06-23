package com.example.dp.ui.subject

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.ui.adapter.AppListAdapter
import com.example.dp.core.ui.adapter.appListAdapter
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.databinding.FragmentSubjectBinding

class SubjectFragment : BaseFragment<FragmentSubjectBinding>(
    FragmentSubjectBinding::inflate
) {
    private val viewModel: SubjectViewModel by appViewModels()

    private val args: SubjectFragmentArgs by navArgs()

    private val onItemClickListener: (SubjectUIModel.UserUIModel) -> Unit = { userModel ->
        findNavController().navigate(
            SubjectFragmentDirections.actionFSubjectToFUserInfo(userModel.ID)
        )
    }

    private val onAbsenceClickListener: (SubjectUIModel.UserUIModel) -> Unit = { userModel ->
        viewModel.onAbsenceClicked(userModel)
    }

    private val getIsFutureDate: () -> Boolean = {
        viewModel.isFutureDate
    }

    private val getIsUserCanEdit: () -> Boolean = {
        viewModel.isUserCanEdit
    }

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        subjectUserAdapterDelegate(
            getIsFutureDate,
            getIsUserCanEdit,
            onItemClickListener,
            onAbsenceClickListener
        )
    )

    override fun initUI() {
        viewModel.subjectID = args.subjectID

        with(binding.rvSubjectMembers) {
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
            onSuccess = { subjectModel ->
                binding.subjectName.text = subjectModel.name
                binding.dateValue.text = subjectModel.date
                recyclerAdapter.submitList(subjectModel.members)
            }
        )
    }
}