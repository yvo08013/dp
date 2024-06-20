package com.example.dp.ui.dashboard

import com.example.dp.data.model.TestEntity


data class DashboardUIModel(
    val title: String
) {
    companion object {
        fun TestEntity.toUIModel(): DashboardUIModel {
            return DashboardUIModel(
                title = "$id $name"
            )
        }
    }
}