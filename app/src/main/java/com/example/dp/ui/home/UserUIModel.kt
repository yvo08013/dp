package com.example.dp.ui.home

import com.example.dp.data.model.UserEntity


data class UserUIModel(
    val name: String,
    val status: UserStatus,
    val displayName: String,
) {
    enum class UserStatus {
        TEACHER,
        STUDENT,
        UNDEFINED,
    }

    companion object {
        fun UserEntity.toUIModel(): UserUIModel {
            return UserUIModel(
                name = name,
                status = when (userType) {
                    1    -> UserStatus.STUDENT
                    2    -> UserStatus.TEACHER
                    else -> UserStatus.UNDEFINED
                },
                displayName = "Привет, ${name} (${
                    when (userType) {
                        1    -> "Студент"
                        2    -> "Преподаватель"
                        else -> "Undefined"
                    }
                })"
            )
        }
    }
}