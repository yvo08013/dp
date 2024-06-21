package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = UserEntity.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = GroupEntity::class,
        parentColumns = arrayOf(GroupEntity.Columns.ID),
        childColumns = arrayOf(UserEntity.Columns.GROUP_ID),
        onUpdate = ForeignKey.NO_ACTION,
        onDelete = ForeignKey.SET_NULL
    )],
    indices = [Index(value = arrayOf(UserEntity.Columns.NAME), unique = true)]
)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = Columns.NAME)
    val name: String,

    @ColumnInfo(name = Columns.PASSWORD)
    val password: String,

    @ColumnInfo(name = Columns.USER_TYPE)
    val userType: Int,

    @ColumnInfo(name = Columns.GROUP_ID)
    val groupID: Int? = null,
) {
    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val PASSWORD = "password"
        const val USER_TYPE = "user_type"
        const val GROUP_ID = "group_id"
    }

    companion object {
        const val TABLE_NAME = "user_table_name"
    }
}