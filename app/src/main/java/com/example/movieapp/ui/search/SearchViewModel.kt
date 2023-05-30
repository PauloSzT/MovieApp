package com.example.movieapp.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.AppDatabase
import com.example.movieapp.data.database.models.FavoriteItem
import com.example.movieapp.data.network.MovieApi
import com.example.movieapp.ui.models.DetailItem
import com.example.movieapp.ui.models.SearchItem
import com.example.movieapp.utils.Constants.MOVIE_ITEM_TYPE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchViewModel(context: Context) : ViewModel() {

    private var isLoading = MutableStateFlow(false)
    private var searchResultList = MutableStateFlow<MutableList<SearchItem>>(mutableListOf())
    private var searchValue = MutableStateFlow("")
    private var dataBase = AppDatabase.getDatabase(context)
    private var dataBaseDao = dataBase.itemDao()
    private var currentItemDetail = MutableStateFlow<DetailItem?>(null)

    val searchUiState = SearchUiState(
        isLoading = isLoading,
        searchResultList = searchResultList,
        searchValue = searchValue,
        currentItemDetail = currentItemDetail,
        onSearchQueryChange = ::onSearchQueryChange,
        saveFavorite = ::saveFavorite,
        updateDetails = ::updateDetails,
    )

    private fun updateDetails(item: SearchItem) {
        currentItemDetail.value = item.mapToUiModel()
    }

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
        currentItemDetail.value = null
        searchValue.value = query
        isLoading.value = true
        searchResultList.value = mutableListOf()
        viewModelScope.launch {
            val response =
                MovieApi.retrofitService.getItemsBySearch(
                    searchQuery = query,
                    itemType = MOVIE_ITEM_TYPE
                )
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
