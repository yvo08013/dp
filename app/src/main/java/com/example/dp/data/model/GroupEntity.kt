package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = GroupEntity.TABLE_NAME,
    indices = [Index(value = arrayOf(GroupEntity.Columns.NAME), unique = true)]
)
data class GroupEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = Columns.NAME)
    val name: String,

    @ColumnInfo(name = Columns.PASSWORD)
    val password: String,

    @ColumnInfo(name = Columns.ADMIN_ID)
    val adminID: Int,
) {
    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val PASSWORD = "password"
        const val ADMIN_ID = "admin_id"
    }

    companion object {
        const val TABLE_NAME = "group_table_name"
    }
}