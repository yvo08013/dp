package com.example.dp.ui.group_list

import com.example.dp.core.ui.adapter.AppViewHolderModel
import com.example.dp.data.model.UserEntity
import com.example.dp.data.model.pojo.GroupPOJO
import com.example.dp.data.model.pojo.UserPOJO
import com.example.dp.ui.group_list.UiModel.GroupUIModel.AdminUIModel.Companion.toAdminUIModel
import com.example.dp.ui.group_list.UiModel.GroupUIModel.Companion.toUIModel


data class UiModel(
    val groups: List<GroupUIModel>,
    val canCreateGroup: Boolean
) {
    data class GroupUIModel(
        val ID: Int,
        val name: String,
        val admin: AdminUIModel,
        val membersCount: String
    ) : AppViewHolderModel {
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

        companion object {
            fun GroupPOJO.toUIModel(): GroupUIModel {
                val admin = members.find { it.id == adminID }!!.toAdminUIModel()
                return GroupUIModel(
                    ID = id!!,
                    name = name,
                    admin = admin,
                    membersCount = "${members.size} участников"
                )
            }
        }

        override fun areItemsTheSame(other: AppViewHolderModel) =
            other is GroupUIModel &&
            this.ID == other.ID

        override fun areContentsTheSame(other: AppViewHolderModel) =
            other is GroupUIModel &&
            this.name == other.name &&
            this.admin == other.admin &&
            this.membersCount == other.membersCount
    }

    companion object {
        fun UserWithGroupsModel.toUIModel(): UiModel {
            return UiModel(
                groups = groups.map { it.toUIModel() },
                canCreateGroup = user.groupID == null
            )
        }
    }
}