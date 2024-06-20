package com.example.dp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.TestDAO
import com.example.dp.ui.home.HomeUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    testDAO: TestDAO
) : ViewModel() {

    val name: SharedFlow<State<HomeUIModel>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(testDAO.getData("home")) },
            mapDelegate = { it.toUIModel() }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}