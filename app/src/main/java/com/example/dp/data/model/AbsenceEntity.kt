package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = AbsenceEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf(UserEntity.Columns.ID),
            childColumns = arrayOf(AbsenceEntity.Columns.USER_ID),
            onUpdate = ForeignKey.NO_ACTION,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = arrayOf(SubjectEntity.Columns.ID),
            childColumns = arrayOf(AbsenceEntity.Columns.SUBJECT_ID),
            onUpdate = ForeignKey.NO_ACTION,
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class AbsenceEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = Columns.USER_ID)
    val userID: Int,

    @ColumnInfo(name = Columns.SUBJECT_ID)
    val subjectID: Int,
) {
    object Columns {
        const val ID = "id"
        const val USER_ID = "user_id"
        const val SUBJECT_ID = "subject_id"
    }

    companion object {
        const val TABLE_NAME = "absence_table_name"
    }
}