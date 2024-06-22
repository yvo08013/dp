package com.example.dp.ui.subject

import android.util.Log
import com.example.dp.core.ui.adapter.AppViewHolderModel
import com.example.dp.data.model.pojo.SubjectPOJO
import com.example.dp.data.model.pojo.UserPOJO
import com.example.dp.ui.subject.SubjectUIModel.UserUIModel.Companion.toUserUIModel
import java.text.SimpleDateFormat
import java.util.Date


data class SubjectUIModel(
    val ID: Int,
    val date: String,
    val name: String,
    val members: List<UserUIModel>
) {
    data class UserUIModel(
        val ID: Int,
        val name: String,
        val status: String,
        val attendance: String
    ) : AppViewHolderModel {
        enum class UserStatus(val displayName: String) {
            TEACHER("Преподаватель"),
            STUDENT("Студент"),
            UNDEFINED("undefined"),
        }

        companion object {
            fun UserPOJO.toUserUIModel(absence: Boolean): UserUIModel {
                return UserUIModel(
                    ID = id!!,
                    name = name,
                    status = when (userType) {
                        1 -> UserStatus.STUDENT.displayName
                        2 -> UserStatus.TEACHER.displayName
                        else -> UserStatus.UNDEFINED.displayName
                    },
                    attendance = if (absence) "-" else "+"
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
        private val format = SimpleDateFormat("yyyy.MM.dd HH:mm")

        fun SubjectPOJO.toUIModel(): SubjectUIModel {
            return SubjectUIModel(
                ID = id!!,
                date = format.format(Date(date)),
                name = subjectMetadata.name,
                members = group.members.map { user ->
                    user.toUserUIModel(absenceList.any { it.userID == user.id })
                }
            )
        }
    }
}