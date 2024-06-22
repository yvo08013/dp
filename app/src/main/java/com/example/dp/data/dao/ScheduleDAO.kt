package com.example.dp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dp.data.model.SubjectEntity
import com.example.dp.data.model.SubjectMetadataEntity
import com.example.dp.data.model.TeacherMetadataEntity


@Dao
interface ScheduleDAO {

    @Query("SELECT * FROM ${SubjectMetadataEntity.TABLE_NAME}")
    suspend fun getSubjectsMeta(): List<SubjectMetadataEntity>

    @Query("SELECT * FROM ${TeacherMetadataEntity.TABLE_NAME}")
    suspend fun getTeachersMeta(): List<TeacherMetadataEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createSubjectMeta(subject: SubjectMetadataEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createTeacherMeta(teacher: TeacherMetadataEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createSubject(subject: SubjectEntity): Long
}