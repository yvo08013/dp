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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class GroupViewModel @Inject constructor(
    private val groupDAO: GroupDAO,
    private val userDAO: UserDAO,
    private val prefUtils: PrefUtils
) : ViewModel() {

    var groupID: Int = -1

    val groupInfo: SharedFlow<State<GroupUIModel>> by lazy {
        fetchLocal(
            dataProvider = {
                DataSource.LocalFlow(
                    combine(
                        userDAO.getUserFlow(prefUtils.userID),
                        groupDAO.getGroupFlow(groupID),
                        ::UserWithGroupModel
                    )
                )
            },
            mapDelegate = { it.group.toUIModel(it.user) }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    private val _joinResponseState = MutableSharedFlow<Boolean>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val joinResponseState: SharedFlow<Boolean> = _joinResponseState.shareIn(
        viewModelScope, SharingStarted.WhileSubscribed(), 0
    )

    fun joinToGroup(password: String) {
        viewModelScope.launch {
            _joinResponseState.emit(groupDAO.joinToGroup(prefUtils.userID, groupID, password))
        }
    }
}