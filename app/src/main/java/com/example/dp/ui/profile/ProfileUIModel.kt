package com.example.dp.ui.profile

import com.example.dp.data.model.GroupEntity
import com.example.dp.data.model.pojo.UserPOJO
import com.example.dp.ui.profile.ProfileUIModel.GroupUIModel.Companion.toUIModel


data class ProfileUIModel(
    val name: String,
    val status: UserStatus,
    val group: GroupUIModel?,
    val absenceCount: String
) {
    enum class UserStatus(val displayName: String) {
        TEACHER("Преподаватель"),
        STUDENT("Студент"),
        UNDEFINED("undefined"),
    }

    data class GroupUIModel(
        val ID: Int,
        val name: String
    ) {
        companion object {
            fun GroupEntity.toUIModel(): GroupUIModel {
                return GroupUIModel(
                    ID = id!!,
                    name = name
                )
            }
        }
    }

    companion object {
        fun UserPOJO.toUIModel(): ProfileUIModel {
            return ProfileUIModel(
                name = name,
                status = when (userType) {
                    1    -> UserStatus.STUDENT
                    2    -> UserStatus.TEACHER
                    else -> UserStatus.UNDEFINED
                },
                group = group?.toUIModel(),
                absenceCount = absenceList.size.toString()
            )
        }
    }
}