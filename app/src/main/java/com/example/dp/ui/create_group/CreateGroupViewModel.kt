package com.example.dp.ui.create_group

import androidx.lifecycle.ViewModel
import com.example.dp.core.utils.PrefUtils
import com.example.dp.data.dao.GroupDAO
import com.example.dp.data.dao.UserDAO
import javax.inject.Inject

class CreateGroupViewModel @Inject constructor(
    private val groupDAO: GroupDAO,
    private val userDAO: UserDAO,
    private val prefUtils: PrefUtils
) : ViewModel() {



}