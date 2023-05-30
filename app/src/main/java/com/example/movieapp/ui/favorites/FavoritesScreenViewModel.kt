package com.example.movieapp.ui.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.AppDatabase
import com.example.movieapp.data.database.models.FavoriteItem
import com.example.movieapp.ui.models.DetailItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class FavoritesScreenViewModel(context: Context) : ViewModel() {

    private val dataBase = AppDatabase.getDatabase(context)
    private var dataBaseDao = dataBase.itemDao()
    private val currentFavoriteDetail = MutableStateFlow<DetailItem?>(null)
    private val favoriteList =
        dataBaseDao.getAll().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favoriteUiState = FavoriteUiState(
        currentFavoriteDetail = currentFavoriteDetail,
        favoriteList = favoriteList,
        updateDetails = ::updateDetails
    )

    private fun updateDetails(item: FavoriteItem) {
        currentFavoriteDetail.value = item.mapToUiModel()
    }
}
