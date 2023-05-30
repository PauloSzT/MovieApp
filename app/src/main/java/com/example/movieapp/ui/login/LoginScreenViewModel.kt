package com.example.movieapp.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.Constants.DATE_FORMAT
import com.example.movieapp.utils.Constants.PASSWORD_IS
import com.example.movieapp.utils.Constants.SHARED_PREFERENCES_IS_USER_LOGGED
import com.example.movieapp.utils.Constants.SHARED_PREFERENCES_LAST_CONNECTION
import com.example.movieapp.utils.Constants.SHARED_PREFERENCES_USERNAME
import com.example.movieapp.utils.Constants.USER_IS
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LoginScreenViewModel(context: Context) : ViewModel() {

    private val userTextFieldValue = MutableStateFlow("")
    private val passwordTextFieldValue = MutableStateFlow("")
    private val isUserLoggedIn = MutableStateFlow(false)

    private val sharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

    init {
        isUserLoggedIn.value =
            sharedPreferences.getBoolean(SHARED_PREFERENCES_IS_USER_LOGGED, false)
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
        val timestamp: Long = System.currentTimeMillis()
        val formattedDateTime = convertTimestampToDateTime(timestamp)
        return if (userTextFieldValue.value == USER_IS && passwordTextFieldValue.value == PASSWORD_IS) {
            sharedPreferences.edit().putBoolean(SHARED_PREFERENCES_IS_USER_LOGGED, true).apply()
            sharedPreferences.edit()
                .putString(SHARED_PREFERENCES_USERNAME, userTextFieldValue.value).apply()
            sharedPreferences.edit()
                .putString(SHARED_PREFERENCES_LAST_CONNECTION, formattedDateTime).apply()
            true
        } else {
            false
        }
    }

    private fun convertTimestampToDateTime(timestamp: Long): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val date = Date(timestamp)
        return dateFormat.format(date)
    }
}
