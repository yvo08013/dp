package com.example.dp.ui.group_list

import com.example.dp.core.ui.adapter.appAdapterDelegate
import com.example.dp.databinding.RvItemGroupBinding


fun groupAdapterDelegate(
    onItemClickListener: (UiModel.GroupUIModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemGroupBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.root.setOnClickListener { onItemClickListener(itemProvider()) }
    },
    onBind = { binding, item, _ ->
        binding.groupName.text = item.name
        binding.membersCount.text = item.membersCount
    }
)