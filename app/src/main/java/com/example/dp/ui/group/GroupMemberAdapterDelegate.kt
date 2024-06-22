package com.example.dp.ui.group

import com.example.dp.core.ui.adapter.appAdapterDelegate
import com.example.dp.databinding.RvItemGroupMembersBinding


fun groupMemberAdapterDelegate(
    onItemClickListener: (GroupUIModel.UserUIModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemGroupMembersBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.userName.setOnClickListener { onItemClickListener(itemProvider()) }
    },
    onBind = { binding, item, _ ->
        binding.userName.text = item.name
        binding.userStatus.text = item.status
        binding.userAbsence.text = item.absence
    }
)