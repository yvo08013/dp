package com.example.dp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.UserDAO
import com.example.dp.ui.home.UserUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    userDAO: UserDAO,
    private val prefUtils: PrefUtils
) : ViewModel() {

    val userInfo: SharedFlow<State<UserUIModel>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(userDAO.getUser(prefUtils.userID)) },
            mapDelegate = { it.toUIModel() }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}