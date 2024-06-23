package com.example.dp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.CoroutineLauncher
import com.example.dp.core.utils.LaunchStrategy
import com.example.dp.data.dao.UserDAO
import com.example.dp.ui.home.UserUIModel.Companion.toUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userDAO: UserDAO
) : ViewModel() {

    private val updateCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.CANCEL
    )

    private val _usersList = MutableSharedFlow<List<UserUIModel>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val usersList: SharedFlow<List<UserUIModel>> = _usersList.shareIn(
        viewModelScope, SharingStarted.WhileSubscribed(), 0
    )

    init {
        viewModelScope.launch {
            withContext((Dispatchers.IO)) {
                _usersList.emit(
                    userDAO.getUsersPOJO("").map {
                        it.toUIModel()
                    }
                )
            }
        }
    }

    fun updateSearchQuery(inputText: String) {
        updateCoroutine.launch {
            _usersList.emit(
                userDAO.getUsersPOJO(inputText).map {
                    it.toUIModel()
                }
            )
        }
    }
}