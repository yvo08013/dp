package com.example.dp.ui.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.dao.UserDAO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val userDAO: UserDAO
) : ViewModel() {
    private val _inputFlow = MutableSharedFlow<SignInInputUIModel>()

    val signInState = fetchLocal(
        dataProvider = {
            DataSource.LocalFlow(
                _inputFlow.transform {
                    emit(userDAO.getUser(it.name, it.password))
                },
            )
        },
        mapDelegate = { it }
    ).shareIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
    )

    fun onConfirmButtonClicked(name: String, password: String) {
        viewModelScope.launch {
            _inputFlow.emit(SignInInputUIModel(name, password))
        }
    }
}