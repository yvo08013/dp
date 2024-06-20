package com.example.dp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dp.data.model.TestEntity


@Dao
interface TestDAO {
    @Query(
        "SELECT * FROM ${TestEntity.TABLE_NAME} WHERE " +
        "${TestEntity.Columns.NAME} LIKE '%' || :name || '%'"
    )
    suspend fun getData(name: String): TestEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addData(searchInput: List<TestEntity>)
}