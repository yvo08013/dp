package com.example.dp.ui.home

import com.example.dp.core.ui.adapter.AppViewHolderModel
import com.example.dp.data.model.pojo.UserPOJO


data class UserUIModel(
    val ID: Int,
    val name: String,
    val status: UserStatus,
    val groupName: String?
) : AppViewHolderModel {
    enum class UserStatus(val displayName: String) {
        TEACHER("Преподаватель"),
        STUDENT("Студент"),
        UNDEFINED("undefined"),
    }

    companion object {
        fun UserPOJO.toUIModel(): UserUIModel {
            return UserUIModel(
                ID = id!!,
                name = name,
                status = when (userType) {
                    1    -> UserStatus.STUDENT
                    2    -> UserStatus.TEACHER
                    else -> UserStatus.UNDEFINED
                },
                groupName = group?.name ?: "-",
            )
        }
    }


    override fun areItemsTheSame(other: AppViewHolderModel) =
        other is UserUIModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel) =
        other is UserUIModel &&
        this.name == other.name &&
        this.status == other.status &&
        this.groupName == other.groupName
}