package com.example.dp.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.dayInMilliseconds
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.dao.ScheduleDAO
import com.example.dp.data.dao.UserDAO
import com.example.dp.ui.schedule.UIStateModel.SubjectUIModel.Companion.toUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val userDAO: UserDAO,
    private val scheduleDAO: ScheduleDAO,
    private val prefUtils: PrefUtils
) : ViewModel() {

    var targetDate: Long = System.currentTimeMillis()

    private val _selectedDate = MutableSharedFlow<Long>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _groupID = MutableSharedFlow<Int>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val schedule = combine(
        _groupID,
        _selectedDate,
        ::ScheduleRequestModel
    ).flatMapLatest { scheduleRequestModel ->
        fetchLocal(
            dataProvider = {
                DataSource.Local(
                    scheduleDAO.getScheduleSubjects(
                        scheduleRequestModel.groupID,
                        scheduleRequestModel.date,
                        scheduleRequestModel.date + dayInMilliseconds,
                    )
                )
            },
            mapDelegate = { it.map { it.toUIModel() } }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    val user = fetchLocal(
        dataProvider = {
            DataSource.LocalFlow(
                userDAO.getUserWithGroupFlow(prefUtils.userID).transform { user ->
                    user.groupID?.let { _groupID.emit(it) }
                    emit(user)
                }
            )
        },
        mapDelegate = { it }
    )

    fun onDateSelected(date: Long) {
        viewModelScope.launch {
            targetDate = date
            _selectedDate.emit(date)
        }
    }
}