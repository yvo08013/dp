package com.example.dp.ui.user_absence

import com.example.dp.core.ui.adapter.AppViewHolderModel
import com.example.dp.core.utils.absenceItemDateFormat
import com.example.dp.core.utils.toLocalDate
import com.example.dp.data.model.pojo.AbsencePOJO
import java.time.LocalDate
import java.util.Date


data class UserAbsenceUIModel(
    val ID: Int,
    val date: String,
    val subjectID: Int,
    val subjectName: String,
    val teacherName: String,
    val rawDate: LocalDate,
    val dateMillis: Long,
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel) =
        other is UserAbsenceUIModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel) =
        other is UserAbsenceUIModel &&
        this.date == other.date &&
        this.subjectID == other.subjectID &&
        this.subjectName == other.subjectName &&
        this.teacherName == other.teacherName

    companion object {
        fun AbsencePOJO.toUIModel(): UserAbsenceUIModel {
            return UserAbsenceUIModel(
                ID = id!!,
                date = absenceItemDateFormat.format(Date(subject.date)),
                subjectID = subject.id!!,
                subjectName = subject.subjectMetadata.name,
                teacherName = subject.teacherMetadata.name,
                rawDate = subject.date.toLocalDate(),
                dateMillis = subject.date
            )
        }
    }
}

data class UserAbsenceDividerUIModel(
    val date: String,
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel) =
        other is UserAbsenceUIModel &&
        this.date == other.date

    override fun areContentsTheSame(other: AppViewHolderModel) =
        other is UserAbsenceUIModel &&
        this.date == other.date
}