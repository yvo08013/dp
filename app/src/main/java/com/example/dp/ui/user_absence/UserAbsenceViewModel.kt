package com.example.dp.ui.user_absence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.ScheduleDAO
import com.example.dp.ui.user_absence.UserAbsenceUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class UserAbsenceViewModel @Inject constructor(
    private val scheduleDAO: ScheduleDAO,
) : ViewModel() {

    var userID: Int = -1

    val absenceList: SharedFlow<State<List<UserAbsenceUIModel>>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(scheduleDAO.getUserAbsenceList(userID)) },
            mapDelegate = { it.sortedWith(compareByDescending { it.subject.date }).map { it.toUIModel() } }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}