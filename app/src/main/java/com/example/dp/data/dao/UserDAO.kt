package com.example.dp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dp.data.model.UserEntity
import com.example.dp.data.model.pojo.UserPOJO
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDAO {

    @Query(
        "SELECT * FROM ${UserEntity.TABLE_NAME} WHERE " +
        "${UserEntity.Columns.NAME} LIKE '%' || :name || '%' AND " +
        "${UserEntity.Columns.PASSWORD} LIKE '%' || :password || '%'"
    )
    suspend fun getUser(name: String, password: String): UserEntity

    @Query(
        "SELECT * FROM ${UserEntity.TABLE_NAME} WHERE " +
        "${UserEntity.Columns.ID} = :ID"
    )
    suspend fun getUser(ID: Int): UserEntity

    @Query(
        "SELECT * FROM ${UserEntity.TABLE_NAME} WHERE " +
        "${UserEntity.Columns.ID} = :ID"
    )
    suspend fun getUserPOJO(ID: Int): UserPOJO

    @Query(
        "SELECT * FROM ${UserEntity.TABLE_NAME} WHERE " +
        "${UserEntity.Columns.ID} = :ID"
    )
    fun getUserPOJOFlow(ID: Int): Flow<UserPOJO>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createUser(user: UserEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateUser(user: UserEntity)

    @Query(
        "DELETE FROM ${UserEntity.TABLE_NAME} WHERE " +
        "${UserEntity.Columns.ID} = :ID"
    )
    suspend fun deleteUser(ID: Int)
}