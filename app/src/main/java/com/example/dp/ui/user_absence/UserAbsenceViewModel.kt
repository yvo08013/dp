package com.example.dp.ui.user_absence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.ui.adapter.AppViewHolderModel
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.absenceGroupDateFormat
import com.example.dp.core.utils.fetchLocal
import com.example.dp.core.utils.monthNames
import com.example.dp.data.State
import com.example.dp.data.dao.ScheduleDAO
import com.example.dp.ui.user_absence.UserAbsenceUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import java.util.Date
import javax.inject.Inject

class UserAbsenceViewModel @Inject constructor(
    private val scheduleDAO: ScheduleDAO,
) : ViewModel() {

    var userID: Int = -1

    val absenceList: SharedFlow<State<List<AppViewHolderModel>>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(scheduleDAO.getUserAbsenceList(userID)) },
            mapDelegate = { absencePojo ->
                val absenceList = absencePojo
                    .sortedWith(compareByDescending { it.subject.date })
                    .map { it.toUIModel() }
                val groups = absenceList.groupBy { it.rawDate.month }
                val result = mutableListOf<AppViewHolderModel>()
                groups.forEach { (month, items) ->
                    result.add(
                        UserAbsenceDividerUIModel(
                            "${monthNames[month.ordinal]} ${absenceGroupDateFormat.format(Date(items[0].dateMillis))}"
                        )
                    )
                    items.forEach { result.add(it) }
                }
                result.toList()
            }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}