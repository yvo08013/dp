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

class ChooseLessonDialogFragment(listener: LessonDialogListener): BottomSheetDialogFragment() {


    lateinit var binding: ChooseLessonBinding
    private var mBottomSheetListener: LessonDialogListener?=null

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = ChooseLessonBinding.bind(inflater.inflate(R.layout.choose_lesson, container))
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /** attach listener from parent fragment */
        try {
            mBottomSheetListener = context as LessonDialogListener?
        }
        catch (e: ClassCastException){
        }
    }

    interface LessonDialogListener {
        fun chooseLessonClick() // todo add teacher id or smth
    }
}