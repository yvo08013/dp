package com.example.dp.ui.subject

import com.example.dp.core.ui.adapter.appAdapterDelegate
import com.example.dp.databinding.RvItemSubjectMemberBinding


fun subjectUserAdapterDelegate(
    onItemClickListener: (SubjectUIModel.UserUIModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemSubjectMemberBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.root.setOnClickListener { onItemClickListener(itemProvider()) }
    },
    onBind = { binding, item, _ ->
        binding.userName.text = item.name
        binding.userStatus.text = item.status
        binding.userAttendance.text = item.attendance
    }
)