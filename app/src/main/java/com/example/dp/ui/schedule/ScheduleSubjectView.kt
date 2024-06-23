package com.example.dp.ui.schedule

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.dp.R
import com.example.dp.databinding.ItemScheduleSubjectBinding

class ScheduleSubjectView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    companion object {
        private const val DEFAULT_SUBJECT_ORDER: String = ""
        private const val DEFAULT_HAS_SUBJECT = true
    }

    private val binding = ItemScheduleSubjectBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var subjectOrder: String = DEFAULT_SUBJECT_ORDER
        set(value) {
            field = value
            binding.orderText.text = field
        }

    var hasSubject: Boolean = DEFAULT_HAS_SUBJECT
        set(value) {
            field = value
            if (!field) {
                binding.root.setOnClickListener { }
            }
            binding.subjectName.isVisible = field
            binding.teacherName.isVisible = field
            binding.subjectType.isVisible = field
            binding.noSubjectView.isVisible = !field
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ScheduleSubjectView, 0, 0).apply {
            try {
                subjectOrder = getString(
                    R.styleable.ScheduleSubjectView_subjectOrder
                ) ?: DEFAULT_SUBJECT_ORDER
                hasSubject = getBoolean(
                    R.styleable.ScheduleSubjectView_hasSubject, true
                )
            } finally {
                recycle()
            }
        }
    }

    fun bind(
        id: Int,
        subjectName: String,
        teacherName: String,
        subjectType: String,
        onSubjectClicked: (Int) -> Unit
    ) {
        binding.root.setOnClickListener { onSubjectClicked(id) }
        binding.teacherName.text = teacherName
        binding.subjectName.text = subjectName
        binding.subjectType.text = subjectType
    }
}