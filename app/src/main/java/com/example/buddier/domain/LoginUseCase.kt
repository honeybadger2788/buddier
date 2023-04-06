package com.example.buddier.domain

import com.example.buddier.data.LoginResult
import com.example.buddier.data.network.AuthenticationService
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    suspend operator fun invoke(email: String, password: String): LoginResult =
        authenticationService.login(email, password)
}