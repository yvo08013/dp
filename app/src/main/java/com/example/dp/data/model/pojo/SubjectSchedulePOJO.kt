package com.example.dp.data.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.dp.data.model.AbsenceEntity
import com.example.dp.data.model.AttendanceEntity
import com.example.dp.data.model.SubjectEntity
import com.example.dp.data.model.SubjectMetadataEntity
import com.example.dp.data.model.TeacherMetadataEntity


data class SubjectSchedulePOJO(
    @ColumnInfo(name = SubjectEntity.Columns.ID)
    val id: Int? = null,

    @ColumnInfo(name = SubjectEntity.Columns.GROUP_ID)
    val groupID: Int,

    @ColumnInfo(name = SubjectEntity.Columns.TEACHER_ID)
    val teacherID: Int,

    @ColumnInfo(name = SubjectEntity.Columns.SUBJECT_METADATA_ID)
    val subjectMetadataID: Int,

    @ColumnInfo(name = SubjectEntity.Columns.DATE)
    val date: Long,

    @ColumnInfo(name = SubjectEntity.Columns.DAY_SCHEDULE_ORDER)
    val dayScheduleOrder: Int,

    @ColumnInfo(name = SubjectEntity.Columns.ACTIVITY_TYPE)
    val activityType: Int,

    @Relation(
        parentColumn = SubjectEntity.Columns.SUBJECT_METADATA_ID,
        entityColumn = SubjectMetadataEntity.Columns.ID
    )
    val subjectMetadata: SubjectMetadataEntity,

    @Relation(
        parentColumn = SubjectEntity.Columns.TEACHER_ID,
        entityColumn = TeacherMetadataEntity.Columns.ID
    )
    val teacherMetadata: TeacherMetadataEntity,

    @Relation(
        parentColumn = SubjectEntity.Columns.ID,
        entityColumn = AbsenceEntity.Columns.SUBJECT_ID,
        entity = AbsenceEntity::class
    )
    val absenceList: List<AbsenceEntity>,

    @Relation(
        parentColumn = SubjectEntity.Columns.ID,
        entityColumn = AttendanceEntity.Columns.SUBJECT_ID,
        entity = AttendanceEntity::class
    )
    val attendanceList: List<AttendanceEntity>
)