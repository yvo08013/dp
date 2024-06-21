package com.example.dp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.UserDAO
import com.example.dp.ui.profile.ProfileUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userDAO: UserDAO,
    private val prefUtils: PrefUtils
) : ViewModel() {

    val userInfo: SharedFlow<State<ProfileUIModel>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(userDAO.getUserPOJO(prefUtils.userID)) },
            mapDelegate = { it.toUIModel() }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    fun deleteUser() {
        viewModelScope.launch {
            userDAO.deleteUser(prefUtils.userID)
        }
    }
}