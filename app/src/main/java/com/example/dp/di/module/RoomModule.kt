package com.example.dp.di.module

import android.content.Context
import com.example.dp.data.DataBase
import com.example.dp.data.dao.TestDAO
import com.example.dp.data.dao.UserDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideDataBase(context: Context): DataBase {
        return DataBase.getDatabase(context)
    }


    @Provides
    fun testDAO(database: DataBase): TestDAO {
        return database.testDAO
    }

    @Provides
    fun userDAO(database: DataBase): UserDAO {
        return database.userDAO
    }
}