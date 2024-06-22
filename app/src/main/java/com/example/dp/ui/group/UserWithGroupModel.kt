package com.example.dp.ui.group

import com.example.dp.data.model.UserEntity
import com.example.dp.data.model.pojo.GroupPOJO


data class UserWithGroupModel(
    val user: UserEntity,
    val group: GroupPOJO,
)