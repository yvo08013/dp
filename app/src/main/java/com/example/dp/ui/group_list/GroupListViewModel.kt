package com.example.dp.ui.group_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.GroupDAO
import com.example.dp.data.dao.UserDAO
import com.example.dp.ui.group_list.UiModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class GroupListViewModel @Inject constructor(
    private val groupDAO: GroupDAO,
    private val userDAO: UserDAO,
    private val prefUtils: PrefUtils
) : ViewModel() {

    val groupList: SharedFlow<State<UiModel>> by lazy {
        fetchLocal(
            dataProvider = {
                DataSource.LocalFlow(
                    combine(
                        userDAO.getUserFlow(prefUtils.userID),
                        groupDAO.getGroupsFlow(),
                        ::UserWithGroupsModel
                    )
                )
            },
            mapDelegate = { it.toUIModel() }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}