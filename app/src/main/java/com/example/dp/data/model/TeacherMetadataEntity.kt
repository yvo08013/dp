package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = TeacherMetadataEntity.TABLE_NAME,
    indices = [Index(value = arrayOf(TeacherMetadataEntity.Columns.NAME), unique = true)]
)
data class TeacherMetadataEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = Columns.NAME)
    val name: String,

    @ColumnInfo(name = Columns.SUBJECT_ID)
    val subjectID: Int,
) {
    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val SUBJECT_ID = "subject_id"
    }

    companion object {
        const val TABLE_NAME = "teacher_metadata_name"
    }
}