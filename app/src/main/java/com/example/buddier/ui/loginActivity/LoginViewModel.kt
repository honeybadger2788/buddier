package com.example.buddier.ui.loginActivity

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buddier.core.Event
import com.example.buddier.data.LoginResult
import com.example.buddier.domain.LoginUseCase
import com.example.buddier.ui.loginActivity.model.UserLoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _navigateToDetails = MutableLiveData<Event<Boolean>>()
    val navigateToDetails: LiveData<Event<Boolean>>
        get() = _navigateToDetails

    private val _navigateToForgotPassword = MutableLiveData<Event<Boolean>>()
    val navigateToForgotPassword: LiveData<Event<Boolean>>
        get() = _navigateToForgotPassword

    private val _navigateToSignUp = MutableLiveData<Event<Boolean>>()
    val navigateToSignUp: LiveData<Event<Boolean>>
        get() = _navigateToSignUp

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState>
        get() = _viewState

    private var _showErrorDialog = MutableLiveData(UserLoginModel())
    val showErrorDialog: LiveData<UserLoginModel>
        get() = _showErrorDialog

    fun onLoginSelected(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
            loginUser(email, password)
        } else {
            onFieldsChanged(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _viewState.value = LoginViewState(isLoading = true)
            when (val result = loginUseCase(email, password)) {
                LoginResult.Error -> {
                    _showErrorDialog.value =
                        UserLoginModel(email = email, password = password, showErrorDialog = true)
                    _viewState.value = LoginViewState(isLoading = false)
                }
                is LoginResult.Success -> {
                    if (result.verified) {
                        _navigateToDetails.value = Event(true)
                    } else {
                        _navigateToVerifyAccount.value = Event(true)
                    }
                }
                else -> {}
            }
            _viewState.value = LoginViewState(isLoading = false)
        }
    }

    fun onFieldsChanged(email: String, password: String) {
        _viewState.value = LoginViewState(
            isValidEmail = isValidEmail(email),
            isValidPassword = isValidPassword(password)
        )
    }

    fun onForgotPasswordSelected() {
        _navigateToForgotPassword.value = Event(true)
    }

    fun onSignUpSelected() {
        _navigateToSignUp.value = Event(true)
    }

    private fun isValidEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidPassword(password: String): Boolean =
        password.length >= MIN_PASSWORD_LENGTH || password.isEmpty()
}