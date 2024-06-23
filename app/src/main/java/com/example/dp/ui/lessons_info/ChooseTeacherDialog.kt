package com.example.dp.ui.lessons_info

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dp.R
import com.example.dp.databinding.ChooseTeacherBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseTeacherDialogFragment(listener: TeacherDialogListener) : BottomSheetDialogFragment() {


    lateinit var binding: ChooseTeacherBinding
    private var mBottomSheetListener: TeacherDialogListener? = null

    init {
        this.mBottomSheetListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.4f)

            setOnShowListener {
                binding.teacher1.setOnClickListener {
                    mBottomSheetListener?.chooseTeacherClick()
                }
                binding.teacher2.setOnClickListener {
                    mBottomSheetListener?.chooseTeacherClick()
                }
                binding.teacher3.setOnClickListener {
                    mBottomSheetListener?.chooseTeacherClick()
                }
                binding.teacher4.setOnClickListener {
                    mBottomSheetListener?.chooseTeacherClick()
                }
                binding.teacher5.setOnClickListener {
                    mBottomSheetListener?.chooseTeacherClick()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ChooseTeacherBinding.bind(inflater.inflate(R.layout.choose_teacher, container))
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mBottomSheetListener = context as TeacherDialogListener?
        } catch (e: ClassCastException) {
            //no-op
        }
    }

    interface TeacherDialogListener {
        fun chooseTeacherClick() // todo add teacher id or smth
    }
}