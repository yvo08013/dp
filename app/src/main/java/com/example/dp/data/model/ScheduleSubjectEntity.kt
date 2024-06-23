package com.example.dp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = ScheduleSubjectEntity.TABLE_NAME,
)
data class ScheduleSubjectEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    val ID: String,

    @ColumnInfo(name = Columns.WEEK_DAY_ORDER)
    val weekDayOrder: Int,

    @ColumnInfo(name = Columns.IS_UPPER_WEEK)
    val isUpperWeek: Boolean,

    @ColumnInfo(name = Columns.ACTIVITY_TYPE)
    val activityType: Int,

    @ColumnInfo(name = Columns.SUBJECT_META_ID)
    val subjectMetaID: Int,

    @ColumnInfo(name = Columns.TEACHER_META_ID)
    val teacherMetaID: Int,

    @ColumnInfo(name = Columns.DAY_SCHEDULE_ORDER)
    val dayScheduleOrder: Int,
) {
    object Columns {
        const val ID = "id"
        const val WEEK_DAY_ORDER = "week_day_order"
        const val IS_UPPER_WEEK = "is_upper_week"
        const val ACTIVITY_TYPE = "activity_type"
        const val SUBJECT_META_ID = "subject_meta_id"
        const val TEACHER_META_ID = "teacher_meta_id"
        const val DAY_SCHEDULE_ORDER = "day_schedule_order"
    }

    fun isValid() =
        activityType != 0 &&
        subjectMetaID != 0 &&
        teacherMetaID != 0

    companion object {
        const val TABLE_NAME = "schedule_subject_table_name"
    }
}