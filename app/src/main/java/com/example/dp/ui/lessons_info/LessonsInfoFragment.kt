package com.example.dp.ui.lessons_info

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.utils.appViewModels
import com.example.dp.databinding.LessonsInfoBinding


class LessonsInfoFragment : BaseFragment<LessonsInfoBinding>(
    LessonsInfoBinding::inflate
), ChooseTeacherDialogFragment.TeacherDialogListener, ChooseLessonDialogFragment.LessonDialogListener {

    private val viewModel: LessonsInfoViewModel by appViewModels()
    private lateinit var dialog: ChooseTeacherDialogFragment /** BottomSheetDialogFragment for choose card */
    private lateinit var listener: ChooseTeacherDialogFragment.TeacherDialogListener/** Listener for choose card */
    private lateinit var dialogLesson: ChooseLessonDialogFragment /** BottomSheetDialogFragment for choose card */
    private lateinit var listenerLesson: ChooseLessonDialogFragment.LessonDialogListener/** Listener for choose card */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = this
        listenerLesson = this
    }

    override fun initUI() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
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
//        observeState(
//            dataFlow = viewModel.groupList,
//            useLoadingData = true,
//            onSuccess = { uiModel ->
//                binding.btnCreate.isVisible = uiModel.canCreateGroup
//                recyclerAdapter.submitList(uiModel.groups)
//            }
//        )
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