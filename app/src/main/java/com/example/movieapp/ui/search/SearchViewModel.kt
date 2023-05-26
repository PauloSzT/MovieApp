package com.example.movieapp.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.network.MovieApi
import com.example.movieapp.network.models.SearchItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private var isLoading = MutableStateFlow(false)
    private var searchResultList = MutableStateFlow<List<SearchItem>>(emptyList())
    private var searchValue = MutableStateFlow<String>("")

    val searchUiState = SearchUiState(
        isLoading = isLoading,
        searchResultList = searchResultList,
        searchValue = searchValue,
        onSearchQueryChange = ::onSearchQueryChange
    )

    init {
        getMoviesApi()
    }

    /**
     * Gets videos information from the Movie API
     */
    private fun getMoviesApi() {
        viewModelScope.launch {
            runCatching { MovieApi.retrofitService.getMovie(movieId = "i") }
        }
    }

    private fun onSearchQueryChange(query: String) {
        searchValue.value = query
        Log.wtf("paulocode", "$query")
        isLoading.value = true
        searchResultList.value = emptyList()
        viewModelScope.launch {
            val response =
                MovieApi.retrofitService.getItemsBySearch(searchQuery = query, itemType = "movie")
            response?.let { searchResults ->
                searchResultList.value = searchResults.collection ?: emptyList()
            }
            isLoading.value = false
        }
    }
}
