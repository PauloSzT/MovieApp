package com.example.movieapp.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.AppDatabase
import com.example.movieapp.data.network.MovieApi
import com.example.movieapp.data.network.models.SearchItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(context: Context) : ViewModel() {

    private var isLoading = MutableStateFlow(false)
    private var searchResultList = MutableStateFlow<List<SearchItem>>(emptyList())
    private var searchValue = MutableStateFlow("")
    private var dataBase = AppDatabase.getDatabase(context)
    private var dataBaseDao = dataBase.itemDao()

    val searchUiState = SearchUiState(
        isLoading = isLoading,
        searchResultList = searchResultList,
        searchValue = searchValue,
        onSearchQueryChange = ::onSearchQueryChange,
        saveFavorite = ::saveFavorite
    )

    private fun saveFavorite(searchItem: SearchItem){
        viewModelScope.launch {dataBaseDao.insertItem(item = searchItem.mapToDataBaseModel())}
    }

    private fun onSearchQueryChange(query: String) {
        searchValue.value = query
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
