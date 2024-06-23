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

class ChooseTeacherDialogFragment(listener: TeacherDialogListener): BottomSheetDialogFragment() {


    lateinit var binding: ChooseTeacherBinding
    private var mBottomSheetListener: TeacherDialogListener?=null

    init {
        this.mBottomSheetListener = listener
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.4f) /** Set dim amount here (the dimming factor of the parent fragment) */

            /** IMPORTANT! Here we set transparency to dialog layer */
            setOnShowListener {
//                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                //bottomSheet.setBackgroundResource(android.R.color.transparent)

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = ChooseTeacherBinding.bind(inflater.inflate(R.layout.choose_teacher, container))
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /** attach listener from parent fragment */
        try {
            mBottomSheetListener = context as TeacherDialogListener?
        }
        catch (e: ClassCastException){
        }
    }

    interface TeacherDialogListener {
        fun chooseTeacherClick() // todo add teacher id or smth
    }
}