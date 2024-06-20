package com.example.dp.ui.home

import com.example.dp.data.model.TestEntity


data class HomeUIModel(
    val title: String
) {
    companion object {
        fun TestEntity.toUIModel(): HomeUIModel {
            return HomeUIModel(
                title = "$id $name"
            )
        }
    }
}