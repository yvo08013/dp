package com.example.dp.ui.home

import com.example.dp.core.ui.adapter.appAdapterDelegate
import com.example.dp.databinding.RvItemUserBinding


fun userAdapterDelegate(
    onItemClickListener: (UserUIModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemUserBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.root.setOnClickListener { onItemClickListener(itemProvider()) }
    },
    onBind = { binding, item, _ ->
        binding.userName.text = item.name
        binding.userStatus.text = item.status.displayName
        binding.groupName.text = item.groupName
    }
)