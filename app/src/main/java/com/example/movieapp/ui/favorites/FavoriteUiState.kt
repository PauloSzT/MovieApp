package com.example.movieapp.ui.favorites

import com.example.movieapp.data.database.models.FavoriteItem
import kotlinx.coroutines.flow.StateFlow

data class FavoriteUiState(
    val favoriteList: StateFlow<List<FavoriteItem>>
)
