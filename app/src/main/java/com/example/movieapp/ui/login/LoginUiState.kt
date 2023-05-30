package com.example.movieapp.ui.login

import kotlinx.coroutines.flow.StateFlow

class LoginUiState(
    val userTextFieldValue: StateFlow<String>,
    val passwordTextFieldValue: StateFlow<String>,
    val isUserLoggedIn: StateFlow<Boolean>,
    val login: () -> Boolean,
    val onUserValueChange: (String) -> Unit,
    val onPasswordValueChange: (String) -> Unit,
    )
