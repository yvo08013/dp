package com.example.dp.ui.group_list

import com.example.dp.data.model.UserEntity
import com.example.dp.data.model.pojo.GroupPOJO


data class UserWithGroupsModel(
    val user: UserEntity,
    val groups: List<GroupPOJO>,
)