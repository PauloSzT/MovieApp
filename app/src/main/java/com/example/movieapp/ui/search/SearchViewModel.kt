package com.example.movieapp.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.AppDatabase
import com.example.movieapp.data.network.MovieApi
import com.example.movieapp.ui.models.DetailItem
import com.example.movieapp.ui.models.FilterType
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
    private var currentItemDetail = MutableStateFlow<DetailItem?>(null)
    private var filterTypeList = MutableStateFlow(
        listOf(
            FilterType.Movies,
            FilterType.Series,
            FilterType.Episodes
        )
    )

    val searchUiState = SearchUiState(
        isLoading = isLoading,
        searchResultList = searchResultList,
        searchValue = searchValue,
        currentItemDetail = currentItemDetail,
        filterTypeList = filterTypeList,
        onSearchQueryChange = ::onSearchQueryChange,
        saveFavorite = ::saveFavorite,
        updateDetails = ::updateDetails,
        onFilterClick = ::onFilterClick
    )

    private fun onFilterClick(type: FilterType) {
        if (filterTypeList.value.contains(type)) {
            filterTypeList.value = filterTypeList.value.filter { filterType ->
                type != filterType
            }
        } else {
            filterTypeList.value = filterTypeList.value + type
        }
        onSearchQueryChange(searchValue.value)
    }

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
        val newList = mutableListOf<SearchItem>()
        viewModelScope.launch {
            val idsList = dataBaseDao.getAllIds().first()
            filterTypeList.value.forEach { filterType ->
                val response =
                    MovieApi.retrofitService.getItemsBySearch(
                        searchQuery = query,
                        itemType = filterType.filter
                    )
                response?.let { searchResults ->
                    searchResults.collection?.forEach { searchItem ->
                        newList.add(searchItem.mapToUiModel(idsList))
                    }
                }
            }
            searchResultList.value = newList
            isLoading.value = false
        }
    }
}
