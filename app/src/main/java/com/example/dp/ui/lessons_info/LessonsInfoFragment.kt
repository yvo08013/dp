package com.example.dp.ui.lessons_info

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.observe
import com.example.dp.databinding.LessonsInfoBinding
import kotlinx.coroutines.flow.collectLatest


class LessonsInfoFragment : BaseFragment<LessonsInfoBinding>(
    LessonsInfoBinding::inflate
), ChooseTeacherDialogFragment.TeacherDialogListener, ChooseLessonDialogFragment.LessonDialogListener {

    private val viewModel: LessonsInfoViewModel by appViewModels()
    private lateinit var dialog: ChooseTeacherDialogFragment

    private lateinit var listener: ChooseTeacherDialogFragment.TeacherDialogListener

    private lateinit var dialogLesson: ChooseLessonDialogFragment

    private lateinit var listenerLesson: ChooseLessonDialogFragment.LessonDialogListener

    private val args: LessonsInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = this
        listenerLesson = this
    }

    override fun initUI() {
        viewModel.weekDayOrder = args.weekDayOrder
        viewModel.isUpperWeek = args.isUpperWeek

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnCreate.setOnClickListener {
            viewModel.createScheduleSubjects()
        }

        binding.radioGroup1.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.scheduleSubject1 = viewModel.scheduleSubject1.copy(
                activityType = when (i) {
                    binding.lessonTypeLecture.id  -> 1
                    binding.lessonTypePractice.id -> 2
                    else                          -> 3
                }
            )
        }
        binding.radioGroup2.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.scheduleSubject2 = viewModel.scheduleSubject2.copy(
                activityType = when (i) {
                    binding.secondLessonTypeLecture.id  -> 1
                    binding.secondLessonTypePractice.id -> 2
                    else                                -> 3
                }
            )
        }
        binding.radioGroup3.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.scheduleSubject3 = viewModel.scheduleSubject1.copy(
                activityType = when (i) {
                    binding.thirdLessonTypeLecture.id  -> 1
                    binding.thirdLessonTypePractice.id -> 2
                    else                               -> 3
                }
            )
        }
        binding.radioGroup4.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.scheduleSubject4 = viewModel.scheduleSubject1.copy(
                activityType = when (i) {
                    binding.fourthLessonTypeLecture.id  -> 1
                    binding.fourthLessonTypePractice.id -> 2
                    else                                -> 3
                }
            )
        }
        binding.radioGroup5.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.scheduleSubject5 = viewModel.scheduleSubject1.copy(
                activityType = when (i) {
                    binding.fithLessonTypeLecture.id  -> 1
                    binding.fithLessonTypePractice.id -> 2
                    else                              -> 3
                }
            )
        }

        binding.firstLessonCardView.setOnClickListener {
            if (binding.hiddenView.isVisible) {
                TransitionManager.beginDelayedTransition(binding.firstLessonCardView, AutoTransition())
                binding.hiddenView.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(binding.firstLessonCardView, AutoTransition())
                binding.hiddenView.visibility = View.VISIBLE
            }
        }
        binding.firstLessonTeacher.setOnClickListener {
            dialog = ChooseTeacherDialogFragment(listener)
            dialog.show(this.parentFragmentManager, "tag")
        }
        binding.firstLessonLesson.setOnClickListener {
            dialogLesson = ChooseLessonDialogFragment(listenerLesson)
            dialogLesson.show(this.parentFragmentManager, "tag")
        }

        binding.secondLessonCardView.setOnClickListener {
            if (binding.secondHiddenView.isVisible) {
                TransitionManager.beginDelayedTransition(binding.secondLessonCardView, AutoTransition())
                binding.secondHiddenView.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(binding.secondLessonCardView, AutoTransition())
                binding.secondHiddenView.visibility = View.VISIBLE
            }
        }
        binding.secondLessonTeacher.setOnClickListener {
            dialog = ChooseTeacherDialogFragment(listener)
            dialog.show(this.parentFragmentManager, "tag")
        }
        binding.secondLessonLesson.setOnClickListener {
            dialogLesson = ChooseLessonDialogFragment(listenerLesson)
            dialogLesson.show(this.parentFragmentManager, "tag")
        }

        binding.thirdLessonCardView.setOnClickListener {
            if (binding.thirdHiddenView.isVisible) {
                TransitionManager.beginDelayedTransition(binding.thirdLessonCardView, AutoTransition())
                binding.thirdHiddenView.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(binding.thirdLessonCardView, AutoTransition())
                binding.thirdHiddenView.visibility = View.VISIBLE
            }
        }
        binding.thirdLessonTeacher.setOnClickListener {
            dialog = ChooseTeacherDialogFragment(listener)
            dialog.show(this.parentFragmentManager, "tag")
        }
        binding.thirdLessonLesson.setOnClickListener {
            dialogLesson = ChooseLessonDialogFragment(listenerLesson)
            dialogLesson.show(this.parentFragmentManager, "tag")
        }

        binding.fourthLessonCardView.setOnClickListener {
            if (binding.fourthHiddenView.isVisible) {
                TransitionManager.beginDelayedTransition(binding.fourthLessonCardView, AutoTransition())
                binding.fourthHiddenView.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(binding.fourthLessonCardView, AutoTransition())
                binding.fourthHiddenView.visibility = View.VISIBLE
            }
        }
        binding.fourthLessonTeacher.setOnClickListener {
            dialog = ChooseTeacherDialogFragment(listener)
            dialog.show(this.parentFragmentManager, "tag")
        }
        binding.fourthLessonLesson.setOnClickListener {
            dialogLesson = ChooseLessonDialogFragment(listenerLesson)
            dialogLesson.show(this.parentFragmentManager, "tag")
        }

        binding.fithLessonCardView.setOnClickListener {
            if (binding.fithHiddenView.isVisible) {
                TransitionManager.beginDelayedTransition(binding.fithLessonCardView, AutoTransition())
                binding.fithHiddenView.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(binding.fithLessonCardView, AutoTransition())
                binding.fithHiddenView.visibility = View.VISIBLE
            }
        }
        binding.fithLessonTeacher.setOnClickListener {
            dialog = ChooseTeacherDialogFragment(listener)
            dialog.show(this.parentFragmentManager, "tag")
        }
        binding.fithLessonLesson.setOnClickListener {
            dialogLesson = ChooseLessonDialogFragment(listenerLesson)
            dialogLesson.show(this.parentFragmentManager, "tag")
        }
    }

    override fun subscribeUI() {
        observe(Lifecycle.State.STARTED) {
            viewModel.saveResponseState.collectLatest {
                findNavController().popBackStack()
            }
        }
    }

    override fun chooseTeacherClick() {
        // todo update viewmodel and ui
        dialog.dismiss()
    }

    override fun chooseLessonClick() {
        // todo update viewmodel and ui
        dialogLesson.dismiss()
    }
}