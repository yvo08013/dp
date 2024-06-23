package com.example.dp.ui.schedule

import com.example.dp.data.model.UserEntity
import com.example.dp.data.model.pojo.GroupPOJO
import com.example.dp.data.model.pojo.SubjectSchedulePOJO


data class DaoRequestModel(
    val user: UserEntity,
    val group: GroupPOJO,
    val subjects: List<SubjectSchedulePOJO>
)