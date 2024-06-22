package com.example.dp.ui.user_absence

import com.example.dp.core.ui.adapter.appAdapterDelegate
import com.example.dp.databinding.RvItemUserAbsenceBinding


fun absenceAdapterDelegate(
    onItemClickListener: (UserAbsenceUIModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemUserAbsenceBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.root.setOnClickListener { onItemClickListener(itemProvider()) }
    },
    onBind = { binding, item, _ ->
        binding.absenceDate.text = item.date
        binding.subjectName.text = item.subjectName
        binding.teacherName.text = item.teacherName
    }
)