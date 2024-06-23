package com.example.dp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dp.data.dao.GroupDAO
import com.example.dp.data.dao.ScheduleDAO
import com.example.dp.data.dao.UserDAO
import com.example.dp.data.model.AbsenceEntity
import com.example.dp.data.model.AttendanceEntity
import com.example.dp.data.model.GroupEntity
import com.example.dp.data.model.ScheduleSubjectEntity
import com.example.dp.data.model.SubjectEntity
import com.example.dp.data.model.SubjectMetadataEntity
import com.example.dp.data.model.TeacherMetadataEntity
import com.example.dp.data.model.UserEntity


@Database(
    exportSchema = false,
    version = 1,
    entities = [
        UserEntity::class,
        GroupEntity::class,
        SubjectMetadataEntity::class,
        TeacherMetadataEntity::class,
        SubjectEntity::class,
        ScheduleSubjectEntity::class,
        AttendanceEntity::class,
        AbsenceEntity::class,
    ]
)
abstract class DataBase : RoomDatabase() {
    abstract val userDAO: UserDAO

    abstract val groupDAO: GroupDAO

    abstract val scheduleDAO: ScheduleDAO

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