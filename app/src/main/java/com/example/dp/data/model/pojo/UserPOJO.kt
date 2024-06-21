package com.example.dp.data.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.dp.data.model.AbsenceEntity
import com.example.dp.data.model.GroupEntity
import com.example.dp.data.model.UserEntity

data class UserPOJO(
    @ColumnInfo(name = UserEntity.Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = UserEntity.Columns.NAME)
    val name: String,

    @ColumnInfo(name = UserEntity.Columns.PASSWORD)
    val password: String,

    @ColumnInfo(name = UserEntity.Columns.USER_TYPE)
    val userType: Int,

    @ColumnInfo(name = UserEntity.Columns.GROUP_ID)
    val groupID: Int? = null,

    @Relation(
        parentColumn = UserEntity.Columns.ID,
        entityColumn = AbsenceEntity.Columns.USER_ID,
        entity = AbsenceEntity::class
    )
    val absenceList: List<AbsenceEntity>,

    @Relation(
        parentColumn = UserEntity.Columns.GROUP_ID,
        entityColumn = GroupEntity.Columns.ID,
        entity = GroupEntity::class
    )
    val group: GroupEntity?,
)