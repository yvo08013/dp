package com.example.dp.ui.create_group

import androidx.navigation.fragment.findNavController
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.appViewModels
import com.example.dp.databinding.CreateGroupBinding

class CreateGroupFragment : BaseFragment<CreateGroupBinding>(
    CreateGroupBinding::inflate
) {
    private val viewModel: CreateGroupViewModel by appViewModels()

    override fun initUI() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.upperWeekMonday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.upperWeekTuesday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.upperWeekWednesday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.upperWeekThursday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.upperWeekFriday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.upperWeekSaturday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.upperWeekSunday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }



        binding.downWeekMonday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.downWeekTuesday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.downWeekWednesday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.downWeekThursday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.downWeekFriday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.downWeekSaturday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
        binding.downWeekSunday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo()
            )
        }
    }

    override fun subscribeUI() {
//        observeState(
//            dataFlow = viewModel.groupList,
//            useLoadingData = true,
//            onSuccess = { uiModel ->
//                binding.btnCreate.isVisible = uiModel.canCreateGroup
//                recyclerAdapter.submitList(uiModel.groups)
//            }
//        )
    }
}