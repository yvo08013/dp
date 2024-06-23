package com.example.dp.ui.create_group

import android.text.Editable
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.dp.R
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observe
import com.example.dp.databinding.CreateGroupBinding
import kotlinx.coroutines.flow.collectLatest

class CreateGroupFragment : BaseFragment<CreateGroupBinding>(
    CreateGroupBinding::inflate
) {
    private val viewModel: CreateGroupViewModel by appViewModels()

    override fun initUI() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnCreate.setOnClickListener {
            viewModel.onCreateGroupClicked()
        }
        binding.groupNameInput.doOnTextChanged { _, _, _, _ ->
            viewModel.groupName = binding.groupNameInput.text.toString()
            binding.btnCreate.isEnabled = isValidInput()
        }
        binding.groupPasswordInput.doOnTextChanged { _, _, _, _ ->
            viewModel.groupPassword = binding.groupPasswordInput.text.toString()
            binding.btnCreate.isEnabled = isValidInput()
        }

        binding.groupNameInput.text = Editable.Factory.getInstance().newEditable(viewModel.groupName)
        binding.groupPasswordInput.text = Editable.Factory.getInstance().newEditable(viewModel.groupPassword)

        binding.upperWeekMonday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(1, true)
            )
        }
        binding.upperWeekTuesday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(2, true)
            )
        }
        binding.upperWeekWednesday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(3, true)
            )
        }
        binding.upperWeekThursday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(4, true)
            )
        }
        binding.upperWeekFriday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(5, true)
            )
        }
        binding.upperWeekSaturday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(6, true)
            )
        }
        binding.upperWeekSunday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(7, true)
            )
        }



        binding.downWeekMonday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(1, false)
            )
        }
        binding.downWeekTuesday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(2, false)
            )
        }
        binding.downWeekWednesday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(3, false)
            )
        }
        binding.downWeekThursday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(4, false)
            )
        }
        binding.downWeekFriday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(5, false)
            )
        }
        binding.downWeekSaturday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(6, false)
            )
        }
        binding.downWeekSunday.setOnClickListener {
            findNavController().navigate(
                CreateGroupFragmentDirections.actionFCreateGroupToFLessonsInfo(7, false)
            )
        }
    }

    override fun subscribeUI() {
        observe(Lifecycle.State.STARTED) {
            viewModel.createResponseState.collectLatest { isSuccess ->
                if (!isSuccess) {
                    Toast.makeText(
                        binding.root.context,
                        R.string.error_creating_group,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun isValidInput() =
        binding.groupNameInput.text.length > 2 &&
        binding.groupPasswordInput.text.length > 4
}