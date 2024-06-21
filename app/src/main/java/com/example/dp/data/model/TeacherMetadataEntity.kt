package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


//TODO create predefined pool of available teachers
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
) {
    object Columns {
        const val ID = "id"
        const val NAME = "name"
    }

    companion object {
        const val TABLE_NAME = "teacher_metadata_name"
    }
}