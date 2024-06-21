package com.example.dp.di.module

import android.content.Context
import com.example.dp.data.DataBase
import com.example.dp.data.dao.GroupDAO
import com.example.dp.data.dao.ScheduleDAO
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
    fun userDAO(database: DataBase): UserDAO {
        return database.userDAO
    }

    @Provides
    fun groupDAO(database: DataBase): GroupDAO {
        return database.groupDAO
    }

    @Provides
    fun scheduleDAO(database: DataBase): ScheduleDAO {
        return database.scheduleDAO
    }
}
