package com.example.movieapp

import kotlinx.coroutines.flow.StateFlow

data class MainActivityUiState(

    val userName: StateFlow<String>,
    val loggedTime: StateFlow<String>,
    val deleteAllItems: () -> Unit,
    val updateUserInfo: () -> Unit
)
