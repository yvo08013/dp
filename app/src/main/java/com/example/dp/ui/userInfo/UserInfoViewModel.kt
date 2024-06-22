package com.example.dp.ui.userInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.UserDAO
import com.example.dp.ui.userInfo.UserInfoUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(
    private val userDAO: UserDAO
) : ViewModel() {

    var userGroupID: Int? = null

    var userID: Int? = null

    val userInfo: SharedFlow<State<UserInfoUIModel>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.LocalFlow(userDAO.getUserPOJOFlow(userID!!)) },
            mapDelegate = { it.toUIModel() }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}