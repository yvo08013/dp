package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = TestEntity.TABLE_NAME,
    indices = [Index(value = arrayOf(TestEntity.Columns.NAME), unique = true)]
)
data class TestEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    val id: Int,

    @ColumnInfo(name = Columns.NAME)
    val name: String,

    ) {
    object Columns {
        const val ID = "id"
        const val NAME = "name"
    }

    companion object {
        const val TABLE_NAME = "test_table_name"
    }
}