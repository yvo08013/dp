package com.example.dp.ui.lessons_info

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dp.R
import com.example.dp.databinding.ChooseLessonBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseLessonDialogFragment(listener: LessonDialogListener) : BottomSheetDialogFragment() {


    lateinit var binding: ChooseLessonBinding
    private var mBottomSheetListener: LessonDialogListener? = null

    init {
        this.mBottomSheetListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.4f)

            setOnShowListener {
                binding.lesson1.setOnClickListener {
                    mBottomSheetListener?.chooseLessonClick()
                }
                binding.lesson2.setOnClickListener {
                    mBottomSheetListener?.chooseLessonClick()
                }
                binding.lesson3.setOnClickListener {
                    mBottomSheetListener?.chooseLessonClick()
                }
                binding.lesson4.setOnClickListener {
                    mBottomSheetListener?.chooseLessonClick()
                }
                binding.lesson5.setOnClickListener {
                    mBottomSheetListener?.chooseLessonClick()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ChooseLessonBinding.bind(inflater.inflate(R.layout.choose_lesson, container))
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mBottomSheetListener = context as LessonDialogListener?
        } catch (e: ClassCastException) {
            //no-op
        }
    }

    interface LessonDialogListener {
        fun chooseLessonClick() // todo add teacher id or smth
    }
}