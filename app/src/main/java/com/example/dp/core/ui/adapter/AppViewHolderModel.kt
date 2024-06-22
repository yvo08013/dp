package com.example.dp.core.ui.adapter


interface AppViewHolderModel {
    fun areItemsTheSame(other: AppViewHolderModel): Boolean

    fun areContentsTheSame(other: AppViewHolderModel): Boolean

    fun getChangePayload(other: AppViewHolderModel): Any? = null
}