package com.example.dp.ui.user_absence

import com.example.dp.core.ui.adapter.AppViewHolderModel
import com.example.dp.data.model.pojo.AbsencePOJO
import java.text.SimpleDateFormat
import java.util.Date


data class UserAbsenceUIModel(
    val ID: Int,
    val date: String,
    val subjectName: String,
    val teacherName: String,
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel) =
        other is UserAbsenceUIModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel) =
        other is UserAbsenceUIModel &&
        this.date == other.date &&
        this.subjectName == other.subjectName &&
        this.teacherName == other.teacherName

    companion object {
        private val format = SimpleDateFormat("yyyy.MM.dd HH:mm")

        fun AbsencePOJO.toUIModel(): UserAbsenceUIModel {
            return UserAbsenceUIModel(
                ID = id!!,
                date = format.format(Date(subject.date)),
                subjectName = subject.subjectMetadata.name,
                teacherName = subject.teacherMetadata.name,
            )
        }
    }
}