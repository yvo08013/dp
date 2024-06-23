package com.example.dp.ui.subject

import androidx.core.view.isVisible
import com.example.dp.core.ui.adapter.appAdapterDelegate
import com.example.dp.databinding.RvItemSubjectMemberBinding


fun subjectUserAdapterDelegate(
    isAttendanceVisible: () -> Boolean,
    isAttendanceEditable: () -> Boolean,
    onItemClickListener: (SubjectUIModel.UserUIModel) -> Unit,
    onAbsenceClickListener: (SubjectUIModel.UserUIModel) -> Unit,
) = appAdapterDelegate(
    inflate = RvItemSubjectMemberBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.root.setOnClickListener { onItemClickListener(itemProvider()) }
        binding.userAttendance.setOnClickListener { onAbsenceClickListener(itemProvider()) }
    },
    onBind = { binding, item, _ ->
        binding.userName.text = item.name
        binding.userStatus.text = item.status
        binding.userAttendance.isEnabled = isAttendanceEditable()
        binding.userAttendance.isVisible = isAttendanceVisible()
        binding.userAttendance.isChecked = item.attendance != null
    }
)