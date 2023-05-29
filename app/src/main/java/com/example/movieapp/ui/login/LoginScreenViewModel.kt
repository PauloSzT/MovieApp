package com.example.movieapp.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginScreenViewModel(context: Context) : ViewModel() {

    private val userTextFieldValue = MutableStateFlow("")
    private val passwordTextFieldValue = MutableStateFlow("")
    private val isUserLoggedIn = MutableStateFlow(false)
    private val sharedPreferences =
        context.getSharedPreferences("movie_app_shared_prefs", Context.MODE_PRIVATE)

    init {
        isUserLoggedIn.value = sharedPreferences.getBoolean("is_user_logged_in", false)
    }

    val loginUiState = LoginUiState(
        userTextFieldValue = userTextFieldValue,
        passwordTextFieldValue = passwordTextFieldValue,
        isUserLoggedIn = isUserLoggedIn,
        login = ::login,
        onUserValueChange = ::onUserValueChange,
        onPasswordValueChange = ::onPasswordValueChange
    )

    private fun onPasswordValueChange(password: String) {
        passwordTextFieldValue.value = password
    }

    private fun onUserValueChange(user: String) {
        userTextFieldValue.value = user
    }
    private fun login(): Boolean {
        return if(userTextFieldValue.value == "user" && passwordTextFieldValue.value == "1234"){
            sharedPreferences.edit().putBoolean("is_user_logged_in", true).apply()
            true
        }else{
            false
        }
    }
}