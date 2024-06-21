package com.example.dp.data.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.dp.data.model.GroupEntity
import com.example.dp.data.model.UserEntity


data class GroupPOJO(
    @ColumnInfo(name = GroupEntity.Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = GroupEntity.Columns.NAME)
    val name: String,

    @ColumnInfo(name = GroupEntity.Columns.PASSWORD)
    val password: String,

    @ColumnInfo(name = GroupEntity.Columns.ADMIN_ID)
    val adminID: Int,

    @Relation(
        parentColumn = GroupEntity.Columns.ID,
        entityColumn = UserEntity.Columns.GROUP_ID,
        entity = UserEntity::class
    )
    val members: List<UserEntity>
)