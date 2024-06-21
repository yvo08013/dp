package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


//TODO create predefined pool of available subjects
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
) {
    object Columns {
        const val ID = "id"
        const val NAME = "name"
    }

    companion object {
        const val TABLE_NAME = "subject_metadata_name"
    }
}