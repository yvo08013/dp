package com.example.dp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dp.data.model.AbsenceEntity
import com.example.dp.data.model.AttendanceEntity
import com.example.dp.data.model.SubjectEntity
import com.example.dp.data.model.SubjectMetadataEntity
import com.example.dp.data.model.TeacherMetadataEntity
import com.example.dp.data.model.UserEntity
import com.example.dp.data.model.pojo.AbsencePOJO
import com.example.dp.data.model.pojo.SubjectPOJO
import com.example.dp.data.model.pojo.SubjectSchedulePOJO
import kotlinx.coroutines.flow.Flow


@Dao
interface ScheduleDAO {

    @Query(
        "SELECT * FROM ${SubjectEntity.TABLE_NAME} WHERE " +
        "${SubjectEntity.Columns.ID} = :ID"
    )
    fun getSubjectFlow(ID: Int): Flow<SubjectPOJO>

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
    suspend fun createSubjects(subjects: List<SubjectEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createAbsence(absence: AbsenceEntity): Long

    @Query(
        "DELETE FROM ${AbsenceEntity.TABLE_NAME} WHERE " +
        "${AbsenceEntity.Columns.ID} = :ID"
    )
    suspend fun deleteAbsence(ID: Int)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createAbsences(absences: List<AbsenceEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createAttendance(attendance: AttendanceEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createAttendances(attendances: List<AttendanceEntity>)

    @Query(
        "SELECT * FROM ${AbsenceEntity.TABLE_NAME} WHERE " +
        "${AbsenceEntity.Columns.USER_ID} = :userID"
    )
    suspend fun getUserAbsenceList(userID: Int): List<AbsencePOJO>

    @Query(
        "SELECT * FROM ${SubjectEntity.TABLE_NAME} WHERE " +
        "${SubjectEntity.Columns.GROUP_ID} = :groupID AND " +
        "${SubjectEntity.Columns.DATE} > :dateStart AND " +
        "${SubjectEntity.Columns.DATE} < :dateEnd"
    )
    suspend fun getScheduleSubjects(groupID: Int, dateStart: Long, dateEnd: Long): List<SubjectSchedulePOJO>
}
