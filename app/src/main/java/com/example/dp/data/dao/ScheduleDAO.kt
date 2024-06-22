package com.example.dp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dp.data.model.AbsenceEntity
import com.example.dp.data.model.SubjectEntity
import com.example.dp.data.model.SubjectMetadataEntity
import com.example.dp.data.model.TeacherMetadataEntity
import com.example.dp.data.model.pojo.AbsencePOJO
import com.example.dp.data.model.pojo.SubjectPOJO


@Dao
interface ScheduleDAO {

    @Query(
        "SELECT * FROM ${SubjectEntity.TABLE_NAME} WHERE " +
        "${SubjectEntity.Columns.ID} = :ID"
    )
    suspend fun getSubject(ID: Int): SubjectPOJO

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

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createAbsence(subject: AbsenceEntity): Long

    @Query(
        "SELECT * FROM ${AbsenceEntity.TABLE_NAME} WHERE " +
        "${AbsenceEntity.Columns.USER_ID} = :userID"
    )
    suspend fun getUserAbsenceList(userID: Int): List<AbsencePOJO>
}
