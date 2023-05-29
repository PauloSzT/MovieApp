package com.example.movieapp.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.AppDatabase
import com.example.movieapp.data.network.MovieApi
import com.example.movieapp.ui.models.SearchItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchViewModel(context: Context) : ViewModel() {

    private var isLoading = MutableStateFlow(false)
    private var searchResultList = MutableStateFlow<MutableList<SearchItem>>(mutableListOf())
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

    private fun saveFavorite(searchItem: SearchItem) {
        viewModelScope.launch {
            val mappedItem = searchItem.mapToDataBaseModel()
            if (searchItem.isFavorite) {
                dataBaseDao.deleteItem(item = mappedItem)
            } else {
                dataBaseDao.insertItem(item = mappedItem)
            }
            val idsList = dataBaseDao.getAllIds().first()
            searchResultList.value = searchResultList.value.map { item ->
                SearchItem(
                    title = item.title,
                    year = item.year,
                    id = item.id,
                    type = item.type,
                    poster = item.poster,
                    isFavorite = idsList.contains(item.id)
                )
            }.toMutableList()
        }
    }

    private fun onSearchQueryChange(query: String) {
        searchValue.value = query
        isLoading.value = true
        searchResultList.value = mutableListOf()
        viewModelScope.launch {
            val response =
                MovieApi.retrofitService.getItemsBySearch(searchQuery = query, itemType = "movie")
            val idsList = dataBaseDao.getAllIds().first()
            response?.let { searchResults ->
                searchResultList.value =
                    searchResults.collection?.map { it.mapToUiModel(idsList) }?.toMutableList()
                        ?: mutableListOf()
            }
            isLoading.value = false
        }
    }
}
