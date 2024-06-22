package com.example.dp.ui.group

import com.example.dp.core.ui.adapter.AppViewHolderModel
import com.example.dp.data.model.pojo.GroupPOJO
import com.example.dp.data.model.pojo.UserPOJO
import com.example.dp.ui.group.GroupUIModel.AdminUIModel.Companion.toAdminUIModel
import com.example.dp.ui.group.GroupUIModel.UserUIModel.Companion.toUserUIModel


data class GroupUIModel(
    val ID: Int,
    val name: String,
    val admin: AdminUIModel,
    val members: List<UserUIModel>,
    val userGroupStatus: UserGroupStatus,
) {
    enum class UserGroupStatus {
        MEMBER,
        ADMIN,
        GUEST,
    }

    data class AdminUIModel(
        val ID: Int,
        val name: String
    ) {
        companion object {
            fun UserPOJO.toAdminUIModel(): AdminUIModel {
                return AdminUIModel(
                    ID = id!!,
                    name = name
                )
            }
        }
    }

    data class UserUIModel(
        val ID: Int,
        val name: String,
        val status: String,
        val absence: String
    ) : AppViewHolderModel {
        enum class UserStatus(val displayName: String) {
            TEACHER("Преподаватель"),
            STUDENT("Студент"),
            UNDEFINED("undefined"),
        }

        companion object {
            fun UserPOJO.toUserUIModel(): UserUIModel {
                return UserUIModel(
                    ID = id!!,
                    name = name,
                    status = when (userType) {
                        1    -> UserStatus.STUDENT.displayName
                        2    -> UserStatus.TEACHER.displayName
                        else -> UserStatus.UNDEFINED.displayName
                    },
                    absence = "${absenceList.size}ч."
                )
            }
        }

        override fun areItemsTheSame(other: AppViewHolderModel) =
            other is UserUIModel &&
            this.ID == other.ID

        override fun areContentsTheSame(other: AppViewHolderModel) =
            other is UserUIModel &&
            this.name == other.name &&
            this.status == other.status
    }

    companion object {
        fun GroupPOJO.toUIModel(userID: Int): GroupUIModel {
            val admin = members.find { it.id == adminID }!!.toAdminUIModel()
            return GroupUIModel(
                ID = id!!,
                name = name,
                admin = admin,
                members = members.map { it.toUserUIModel() },
                userGroupStatus = when {
                    admin.ID == userID              -> UserGroupStatus.ADMIN
                    members.any { it.id == userID } -> UserGroupStatus.MEMBER
                    else                            -> UserGroupStatus.GUEST
                }
            )
        }
    }
}