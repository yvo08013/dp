package com.example.dp.ui.lessons_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.data.dao.ScheduleDAO
import com.example.dp.data.model.ScheduleSubjectEntity
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LessonsInfoViewModel @Inject constructor(
    private val scheduleDAO: ScheduleDAO
) : ViewModel() {

    var weekDayOrder: Int = 0
    var isUpperWeek: Boolean = false

    var scheduleSubject1 = ScheduleSubjectEntity(
        ID = "1 $weekDayOrder $isUpperWeek",
        weekDayOrder = weekDayOrder,
        isUpperWeek = isUpperWeek,
        activityType = 1,
        subjectMetaID = 1,
        teacherMetaID = 1,
        dayScheduleOrder = 1,
    )

    var scheduleSubject2 = ScheduleSubjectEntity(
        ID = "2 $weekDayOrder $isUpperWeek",
        weekDayOrder = weekDayOrder,
        isUpperWeek = isUpperWeek,
        activityType = 1,
        subjectMetaID = 0,
        teacherMetaID = 0,
        dayScheduleOrder = 2,
    )

    var scheduleSubject3 = ScheduleSubjectEntity(
        ID = "3 $weekDayOrder $isUpperWeek",
        weekDayOrder = weekDayOrder,
        isUpperWeek = isUpperWeek,
        activityType = 1,
        subjectMetaID = 0,
        teacherMetaID = 0,
        dayScheduleOrder = 3,
    )

    var scheduleSubject4 = ScheduleSubjectEntity(
        ID = "4 $weekDayOrder $isUpperWeek",
        weekDayOrder = weekDayOrder,
        isUpperWeek = isUpperWeek,
        activityType = 1,
        subjectMetaID = 1,
        teacherMetaID = 1,
        dayScheduleOrder = 4,
    )

    var scheduleSubject5 = ScheduleSubjectEntity(
        ID = "5 $weekDayOrder $isUpperWeek",
        weekDayOrder = weekDayOrder,
        isUpperWeek = isUpperWeek,
        activityType = 1,
        subjectMetaID = 2,
        teacherMetaID = 2,
        dayScheduleOrder = 5,
    )


    private val _SaveResponseState = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val saveResponseState: SharedFlow<Unit> = _SaveResponseState.shareIn(
        viewModelScope, SharingStarted.WhileSubscribed(), 0
    )

    fun createScheduleSubjects() {
        updateArgs()
        viewModelScope.launch {
            if (scheduleSubject1.isValid()) {
                scheduleDAO.createScheduleSubject(scheduleSubject1)
            } else {
                scheduleDAO.deleteScheduleSubject(scheduleSubject1.ID)
            }
            if (scheduleSubject2.isValid()) {
                scheduleDAO.createScheduleSubject(scheduleSubject2)
            } else {
                scheduleDAO.deleteScheduleSubject(scheduleSubject2.ID)
            }
            if (scheduleSubject3.isValid()) {
                scheduleDAO.createScheduleSubject(scheduleSubject3)
            } else {
                scheduleDAO.deleteScheduleSubject(scheduleSubject3.ID)
            }
            if (scheduleSubject4.isValid()) {
                scheduleDAO.createScheduleSubject(scheduleSubject4)
            } else {
                scheduleDAO.deleteScheduleSubject(scheduleSubject4.ID)
            }
            if (scheduleSubject5.isValid()) {
                scheduleDAO.createScheduleSubject(scheduleSubject5)
            } else {
                scheduleDAO.deleteScheduleSubject(scheduleSubject5.ID)
            }
            _SaveResponseState.emit(Unit)
        }
    }

    private fun updateArgs() {
        scheduleSubject1 = scheduleSubject1.copy(
            ID = "1 $weekDayOrder $isUpperWeek",
            weekDayOrder = weekDayOrder,
            isUpperWeek = isUpperWeek,
        )
        scheduleSubject2 = scheduleSubject2.copy(
            ID = "2 $weekDayOrder $isUpperWeek",
            weekDayOrder = weekDayOrder,
            isUpperWeek = isUpperWeek,
        )
        scheduleSubject3 = scheduleSubject3.copy(
            ID = "3 $weekDayOrder $isUpperWeek",
            weekDayOrder = weekDayOrder,
            isUpperWeek = isUpperWeek,
        )
        scheduleSubject4 = scheduleSubject4.copy(
            ID = "4 $weekDayOrder $isUpperWeek",
            weekDayOrder = weekDayOrder,
            isUpperWeek = isUpperWeek,
        )
        scheduleSubject5 = scheduleSubject5.copy(
            ID = "5 $weekDayOrder $isUpperWeek",
            weekDayOrder = weekDayOrder,
            isUpperWeek = isUpperWeek,
        )
    }
}