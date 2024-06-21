package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = SubjectEntity.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = SubjectMetadataEntity::class,
        parentColumns = arrayOf(SubjectMetadataEntity.Columns.ID),
        childColumns = arrayOf(SubjectEntity.Columns.SUBJECT_METADATA_ID),
        onUpdate = ForeignKey.NO_ACTION,
        onDelete = ForeignKey.CASCADE
    )],
)
data class SubjectEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = Columns.GROUP_ID)
    val groupID: Int,

    @ColumnInfo(name = Columns.TEACHER_ID)
    val teacherID: Int,

    @ColumnInfo(name = Columns.SUBJECT_METADATA_ID)
    val subjectMetadataID: Int,

    @ColumnInfo(name = Columns.DATE)
    val date: Long,

    @ColumnInfo(name = Columns.ACTIVITY_TYPE)
    val activityType: Int,
) {
    object Columns {
        const val ID = "id"
        const val GROUP_ID = "group_id"
        const val TEACHER_ID = "teacher_id"
        const val SUBJECT_METADATA_ID = "subject_metadata_id"
        const val DATE = "date"
        const val ACTIVITY_TYPE = "activity_type"
    }

    companion object {
        const val TABLE_NAME = "subject_name"
    }
}