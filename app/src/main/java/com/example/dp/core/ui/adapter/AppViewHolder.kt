package com.example.dp.core.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class AppViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    lateinit var item: AppViewHolderModel

    abstract fun bind(item: AppViewHolderModel, payloads: List<Any>)
}