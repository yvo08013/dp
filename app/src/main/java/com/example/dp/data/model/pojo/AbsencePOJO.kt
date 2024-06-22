package com.example.dp.data.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.dp.data.model.AbsenceEntity
import com.example.dp.data.model.SubjectEntity

data class AbsencePOJO(
    @ColumnInfo(name = AbsenceEntity.Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = AbsenceEntity.Columns.USER_ID)
    val userID: Int,

    @ColumnInfo(name = AbsenceEntity.Columns.SUBJECT_ID)
    val subjectID: Int,

    @Relation(
        parentColumn = AbsenceEntity.Columns.SUBJECT_ID,
        entityColumn = SubjectEntity.Columns.ID,
        entity = SubjectEntity::class
    )
    val subject: SubjectPOJO,
)