package com.example.movieapp.ui.search

import com.example.movieapp.ui.models.DetailItem
import com.example.movieapp.ui.models.SearchItem
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val isLoading: StateFlow<Boolean>,
    val searchResultList: StateFlow<List<SearchItem>>,
    val searchValue: StateFlow<String>,
    val currentItemDetail: StateFlow<DetailItem?>,
    val onSearchQueryChange: (String) -> Unit,
    val saveFavorite: (SearchItem) -> Unit,
    val updateDetails: (SearchItem) -> Unit
)
