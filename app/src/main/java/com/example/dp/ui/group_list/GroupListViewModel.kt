package com.example.dp.ui.group_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.GroupDAO
import com.example.dp.ui.group_list.GroupUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class GroupListViewModel @Inject constructor(
    private val groupDAO: GroupDAO,
) : ViewModel() {

    var userID: Int = -1

    val groupList: SharedFlow<State<List<GroupUIModel>>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.Local(groupDAO.getGroups()) },
            mapDelegate = { it.map { it.toUIModel() } }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}