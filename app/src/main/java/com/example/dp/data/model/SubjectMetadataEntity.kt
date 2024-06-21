package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = SubjectMetadataEntity.TABLE_NAME,
    indices = [Index(value = arrayOf(SubjectMetadataEntity.Columns.NAME), unique = true)]
)
data class SubjectMetadataEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = Columns.NAME)
    val name: String,

    @ColumnInfo(name = Columns.TEACHER_ID)
    val teacherID: Int,
) {
    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val TEACHER_ID = "teacher_id"
    }

    companion object {
        const val TABLE_NAME = "subject_metadata_name"
    }
}