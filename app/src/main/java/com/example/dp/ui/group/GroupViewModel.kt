package com.example.dp.ui.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.GroupDAO
import com.example.dp.data.dao.UserDAO
import com.example.dp.ui.group.GroupUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class GroupViewModel @Inject constructor(
    private val groupDAO: GroupDAO,
    private val userDAO: UserDAO,
    private val prefUtils: PrefUtils
) : ViewModel() {

    var groupID: Int = -1

    val groupInfo: SharedFlow<State<GroupUIModel>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(groupDAO.getGroup(groupID)) },
            mapDelegate = { it.toUIModel(prefUtils.userID) }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}