package com.example.movieapp.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import com.example.movieapp.network.models.SearchItem
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val isLoading: StateFlow<Boolean>,
    val searchResultList: StateFlow<List<SearchItem>>,
    val searchValue: StateFlow<String>,
    val onSearchQueryChange: (String) -> Unit
)
