package com.example.dp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.TestDAO
import com.example.dp.ui.dashboard.DashboardUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    testDAO: TestDAO
) : ViewModel() {

    val name: SharedFlow<State<DashboardUIModel>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(testDAO.getData("ldfkjgjlkdfgkjldf")) },
            mapDelegate = { it.toUIModel() }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}