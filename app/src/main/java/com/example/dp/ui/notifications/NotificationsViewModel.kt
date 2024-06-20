package com.example.dp.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.TestDAO
import com.example.dp.ui.notifications.NotificationsUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class NotificationsViewModel @Inject constructor(
    testDAO: TestDAO
) : ViewModel() {

    val name: SharedFlow<State<NotificationsUIModel>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(testDAO.getData("notifications")) },
            mapDelegate = { it.toUIModel() }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}