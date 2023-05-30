package com.example.movieapp.ui.favorites

import com.example.movieapp.data.database.models.FavoriteItem
import com.example.movieapp.ui.models.DetailItem
import kotlinx.coroutines.flow.StateFlow

data class FavoriteUiState(
    val favoriteList: StateFlow<List<FavoriteItem>>,
    val currentFavoriteDetail: StateFlow<DetailItem?>,
    val updateDetails: (FavoriteItem) -> Unit
)
