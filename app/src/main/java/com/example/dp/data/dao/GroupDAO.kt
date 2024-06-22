package com.example.dp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.dp.data.model.GroupEntity
import com.example.dp.data.model.UserEntity
import com.example.dp.data.model.pojo.GroupPOJO
import kotlinx.coroutines.flow.Flow


@Dao
interface GroupDAO {

    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME}")
    fun getGroupsFlow(): Flow<List<GroupPOJO>>

    @Query(
        "SELECT * FROM ${GroupEntity.TABLE_NAME} WHERE " +
        "${GroupEntity.Columns.ID} = :ID"
    )
    suspend fun getGroup(ID: Int): GroupPOJO

    @Query(
        "SELECT * FROM ${GroupEntity.TABLE_NAME} WHERE " +
        "${GroupEntity.Columns.ID} = :ID"
    )
    fun getGroupFlow(ID: Int): Flow<GroupPOJO>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createGroup(group: GroupEntity): Long

    @Query(
        "DELETE FROM ${GroupEntity.TABLE_NAME} WHERE " +
        "${GroupEntity.Columns.ID} = :ID"
    )
    suspend fun deleteGroup(ID: Int)


    @Query(
        "SELECT * FROM ${UserEntity.TABLE_NAME} WHERE " +
        "${UserEntity.Columns.ID} = :ID"
    )
    suspend fun getUser(ID: Int): UserEntity

    @Update
    suspend fun updateUser(user: UserEntity)

    @Transaction
    suspend fun joinToGroup(userID: Int, groupID: Int, password: String): Boolean {
        val group = getGroup(groupID)
        if (group.password != password) {
            return false
        } else {
            updateUser(getUser(userID).copy(groupID = groupID))
            return true
        }
    }
}