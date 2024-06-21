package com.example.dp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dp.data.dao.TestDAO
import com.example.dp.data.dao.UserDAO
import com.example.dp.data.model.TestEntity
import com.example.dp.data.model.UserEntity


@Database(
    exportSchema = false,
    version = 1,
    entities = [
        TestEntity::class,
        UserEntity::class,
    ]
)
abstract class DataBase : RoomDatabase() {
    abstract val testDAO: TestDAO

    abstract val userDAO: UserDAO

    companion object {
        private const val DB_NAME = "data_base"

        private lateinit var dataBase: DataBase

        fun getDatabase(
            context: Context,
        ): DataBase {
            if (!Companion::dataBase.isInitialized)
                dataBase = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    DB_NAME
                ).build()
            return dataBase
        }
    }
}