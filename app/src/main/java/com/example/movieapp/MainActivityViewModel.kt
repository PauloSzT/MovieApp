package com.example.movieapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(context: Context) : ViewModel() {

    private var dataBase = AppDatabase.getDatabase(context)
    private var dataBaseDao = dataBase.itemDao()
    private val sharedPreferences =
        context.getSharedPreferences("movie_app_shared_prefs", Context.MODE_PRIVATE)
    private var userName = MutableStateFlow("")
    private var loggedTime = MutableStateFlow("")

    val mainActivityUiState = MainActivityUiState(
        userName = userName,
        loggedTime = loggedTime,
        deleteAllItems = ::deleteAllItems,
        updateUserInfo = ::updateUserInfo
    )

    private fun updateUserInfo(){
        userName.value = sharedPreferences.getString("user_name", "") ?: ""
        loggedTime.value = sharedPreferences.getString("last_date_logged_in", "") ?: ""
    }

    private fun deleteAllItems() {
        sharedPreferences.edit().putBoolean("is_user_logged_in", false).apply()
        sharedPreferences.edit().putString("user_name","").apply()
        sharedPreferences.edit().putString("last_date_logged_in","").apply()
        viewModelScope.launch {
            dataBaseDao.deleteAllItems()
        }
    }
}
