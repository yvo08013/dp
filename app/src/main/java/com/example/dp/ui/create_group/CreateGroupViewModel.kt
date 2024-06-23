package com.example.dp.ui.create_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.ScheduleSubject.Companion.dayOrderToTime
import com.example.dp.core.utils.createListOfDates
import com.example.dp.core.utils.semesterEndDate
import com.example.dp.core.utils.semesterStartDate
import com.example.dp.core.utils.toLocalDate
import com.example.dp.core.utils.toLong
import com.example.dp.data.dao.GroupDAO
import com.example.dp.data.dao.ScheduleDAO
import com.example.dp.data.dao.UserDAO
import com.example.dp.data.model.GroupEntity
import com.example.dp.data.model.SubjectEntity
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

class CreateGroupViewModel @Inject constructor(
    private val groupDAO: GroupDAO,
    private val userDAO: UserDAO,
    private val scheduleDAO: ScheduleDAO,
    private val prefUtils: PrefUtils
) : ViewModel() {

    var groupName: String = ""
    var groupPassword: String = ""

    private val _createResponseState = MutableSharedFlow<Boolean>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val createResponseState: SharedFlow<Boolean> = _createResponseState.shareIn(
        viewModelScope, SharingStarted.WhileSubscribed(), 0
    )

    fun onCreateGroupClicked() {
        viewModelScope.launch {
            try {
                val groupID = groupDAO.createGroup(
                    GroupEntity(
                        name = groupName,
                        password = groupPassword,
                        adminID = prefUtils.userID
                    )
                ).toInt()
                val newUser = userDAO.getUser(prefUtils.userID).copy(groupID = groupID)
                userDAO.updateUser(newUser)

                val subjects = scheduleDAO.getScheduleSubjects()
                val days = subjects.groupBy { it.weekDayOrder }


                val startOfSemester = semesterStartDate.toLocalDate()
                val endOfSemester = semesterEndDate.toLocalDate()

                val subjectsList = mutableListOf<SubjectEntity>()

                days.forEach { (dayOrder, subjects) ->
                    if (subjects.isNotEmpty()) {
                        val isUpperWeek = subjects.first().isUpperWeek
                        val dates = createListOfDates(
                            startDate = startOfSemester,
                            endDate = endOfSemester,
                            dayOfWeek = DayOfWeek.of(dayOrder),
                            isUpperWeek = isUpperWeek
                        )
                        dates.forEach { date ->
                            val epochMilliseconds = date.toLong()
                            subjects.forEach { scheduleSubject ->
                                val subject = SubjectEntity(
                                    groupID = groupID,
                                    teacherID = scheduleSubject.teacherMetaID,
                                    subjectMetadataID = scheduleSubject.subjectMetaID,
                                    date = epochMilliseconds + scheduleSubject.dayScheduleOrder.dayOrderToTime(),
                                    dayScheduleOrder = scheduleSubject.dayScheduleOrder,
                                    activityType = scheduleSubject.activityType,
                                )
                                subjectsList.add(subject)
                            }
                        }
                    }
                }

                scheduleDAO.createSubjects(subjectsList)

                scheduleDAO.clearScheduleSubjects()
                _createResponseState.emit(true)
            } catch (e: Exception) {
                scheduleDAO.clearScheduleSubjects()
                _createResponseState.emit(false)
            }
        }
    }
}