package com.example.movieapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.AppDatabase
import com.example.movieapp.utils.Constants.SHARED_PREFERENCES_IS_USER_LOGGED
import com.example.movieapp.utils.Constants.SHARED_PREFERENCES_LAST_CONNECTION
import com.example.movieapp.utils.Constants.SHARED_PREFERENCES_KEY
import com.example.movieapp.utils.Constants.SHARED_PREFERENCES_USERNAME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(context: Context) : ViewModel() {

    private var dataBase = AppDatabase.getDatabase(context)
    private var dataBaseDao = dataBase.itemDao()
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    private var userName = MutableStateFlow("")
    private var loggedTime = MutableStateFlow("")

    val mainActivityUiState = MainActivityUiState(
        userName = userName,
        loggedTime = loggedTime,
        deleteAllItems = ::deleteAllItems,
        updateUserInfo = ::updateUserInfo
    )

    private fun updateUserInfo() {
        userName.value = sharedPreferences.getString(SHARED_PREFERENCES_USERNAME, "") ?: ""
        loggedTime.value = sharedPreferences.getString(SHARED_PREFERENCES_LAST_CONNECTION, "") ?: ""
    }

    private fun deleteAllItems() {
        sharedPreferences.edit().putBoolean(SHARED_PREFERENCES_IS_USER_LOGGED, false).apply()
        sharedPreferences.edit().putString(SHARED_PREFERENCES_USERNAME, "").apply()
        sharedPreferences.edit().putString(SHARED_PREFERENCES_LAST_CONNECTION, "").apply()
        viewModelScope.launch {
            dataBaseDao.deleteAllItems()
        }
    }
}
