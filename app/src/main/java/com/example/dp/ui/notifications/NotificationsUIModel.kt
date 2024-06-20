package com.example.dp.ui.notifications

import com.example.dp.data.model.TestEntity


data class NotificationsUIModel(
    val title: String
) {
    companion object {
        fun TestEntity.toUIModel(): NotificationsUIModel {
            return NotificationsUIModel(
                title = "$id $name"
            )
        }
    }
}