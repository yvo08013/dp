package com.example.dp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dp.data.model.GroupEntity
import com.example.dp.data.model.pojo.GroupPOJO


@Dao
interface GroupDAO {

    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME}")
    suspend fun getGroups(): List<GroupPOJO>

    @Query(
        "SELECT * FROM ${GroupEntity.TABLE_NAME} WHERE " +
        "${GroupEntity.Columns.ID} = :ID"
    )
    suspend fun getGroup(ID: Int): GroupPOJO

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createGroup(group: GroupEntity): Long

    @Query(
        "DELETE FROM ${GroupEntity.TABLE_NAME} WHERE " +
        "${GroupEntity.Columns.ID} = :ID"
    )
    suspend fun deleteGroup(ID: Int)
}