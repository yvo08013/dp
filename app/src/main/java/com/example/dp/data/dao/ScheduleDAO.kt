package com.example.dp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dp.data.model.SubjectMetadataEntity
import com.example.dp.data.model.TeacherMetadataEntity


@Dao
interface ScheduleDAO {

    @Query("SELECT * FROM ${SubjectMetadataEntity.TABLE_NAME}")
    suspend fun getSubjectsMeta(): List<SubjectMetadataEntity>

    @Query("SELECT * FROM ${TeacherMetadataEntity.TABLE_NAME}")
    suspend fun getTeachersMeta(): List<TeacherMetadataEntity>
}