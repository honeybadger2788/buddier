package com.example.buddier.ui.loginActivity.model

data class UserLoginModel (
    val email: String = "",
    val password: String = "",
    val showErrorDialog: Boolean = false
)