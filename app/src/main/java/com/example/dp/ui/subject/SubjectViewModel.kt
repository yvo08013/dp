package com.example.dp.ui.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.ScheduleDAO
import com.example.dp.ui.subject.SubjectUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class SubjectViewModel @Inject constructor(
    private val scheduleDAO: ScheduleDAO
) : ViewModel() {

    var subjectID: Int = -1

    val groupInfo: SharedFlow<State<SubjectUIModel>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(scheduleDAO.getSubject(subjectID)) },
            mapDelegate = { it.toUIModel() }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}