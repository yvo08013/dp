package com.example.dp.ui.schedule

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.ErrorCodes
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observeState
import com.example.dp.core.utils.semesterEndDate
import com.example.dp.core.utils.semesterStartDate
import com.example.dp.databinding.FragmentScheduleBinding
import java.util.Calendar

class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(
    FragmentScheduleBinding::inflate
) {
    private val viewModel: ScheduleViewModel by appViewModels()

    private val onItemClickListener: (Int) -> Unit = { subjectID ->
        findNavController().navigate(
            ScheduleFragmentDirections.actionFScheduleToFSubject(subjectID)
        )
    }

    override fun initUI() {
        binding.calendarView.minDate = semesterStartDate
        binding.calendarView.maxDate = semesterEndDate
        binding.calendarView.date = viewModel.targetDate
        viewModel.onDateSelected(viewModel.targetDate)
        binding.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            val calendar = Calendar.Builder().setDate(year, month, dayOfMonth).build()
            binding.weekType.text = when (calendar.get(Calendar.WEEK_OF_YEAR) % 2) {
                0 -> "Верхняя неделя"
                else -> "Нижняя неделя"
            }
            viewModel.onDateSelected(calendar.timeInMillis)
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.user,
            useLoadingData = true,
            onSuccess = { user ->
                if (user.group == null) {
                    binding.noGroupAlert.isVisible = true
                    binding.contentContainer.isVisible = false
                } else {
                    binding.noGroupAlert.isVisible = false
                    binding.contentContainer.isVisible = true
                    binding.title.text = "Расписание для группы ${user.group.name}"
                }
            }
        )
        observeState(
            dataFlow = viewModel.schedule,
            useLoadingData = true,
            onSuccess = { scheduleList ->
                binding.noScheduleText.isVisible = false
                binding.subjectsContainer.isVisible = true

                for (index in 0 until binding.subjectsContainer.childCount) {
                    val view = binding.subjectsContainer.getChildAt(index) as ScheduleSubjectView
                    val subject = scheduleList.find { it.dayScheduleOrder - 1 == index }
                    if (subject == null) {
                        view.hasSubject = false
                    } else {
                        view.hasSubject = true
                        view.bind(
                            subject.ID,
                            subject.name,
                            subject.teacherName,
                            subject.subjectType.displayName,
                            onSubjectClicked = onItemClickListener
                        )
                    }
                }
            },
            onFailure = { errorCode, messageID, throwable ->
                when (errorCode) {
                    ErrorCodes.STATE_NO_DATA -> {
                        binding.noScheduleText.isVisible = true
                        binding.subjectsContainer.isVisible = false
                    }
                }
            }
        )
    }
}