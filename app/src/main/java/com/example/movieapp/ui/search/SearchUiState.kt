package com.example.movieapp.ui.search

import com.example.movieapp.data.network.models.SearchItem
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val isLoading: StateFlow<Boolean>,
    val searchResultList: StateFlow<List<SearchItem>>,
    val searchValue: StateFlow<String>,
    val onSearchQueryChange: (String) -> Unit,
    val saveFavorite: (SearchItem) -> Unit
)
