package com.example.dp.ui.schedule

import com.example.dp.data.model.pojo.SubjectSchedulePOJO
import com.example.dp.ui.schedule.UIStateModel.SubjectUIModel.Companion.toUIModel


data class UIStateModel(
    val groupName: String?,
    val subjects: List<SubjectUIModel>
) {
    data class SubjectUIModel(
        val ID: Int,
        val name: String,
        val teacherName: String,
        val dayScheduleOrder: Int,
        val subjectType: SubjectType
    ) {
        enum class SubjectType(val displayName: String) {
            PRACTICE("практика"),
            LECTURE("лекция"),
            LABORATORY("лабораторная")
        }

        companion object {
            fun SubjectSchedulePOJO.toUIModel(): SubjectUIModel {
                return SubjectUIModel(
                    ID = id!!,
                    name = subjectMetadata.name,
                    teacherName = teacherMetadata.name,
                    dayScheduleOrder = dayScheduleOrder,
                    subjectType = when (activityType) {
                        1 -> SubjectType.PRACTICE
                        2 -> SubjectType.LECTURE
                        else -> SubjectType.LABORATORY
                    },
                )
            }
        }
    }

    companion object {
        fun DaoRequestModel.toUIModel(): UIStateModel {
            return UIStateModel(
                groupName = group.name,
                subjects = subjects.map { it.toUIModel() }
            )
        }
    }
}

data class ScheduleRequestModel(
    val groupID: Int,
    val date: Long
)