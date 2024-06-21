package com.example.dp.ui.sign_up

import com.example.dp.data.model.UserEntity


data class SignUpInputUIModel(
    val name: String,
    val password: String,
    val userType: Int,
) {
    companion object {
        fun SignUpInputUIModel.toUserEntity(): UserEntity {
            return UserEntity(
                name = name,
                password = password,
                userType = userType
            )
        }
    }
}