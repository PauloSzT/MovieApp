package com.example.movieapp.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.ui.details.DetailsBottomSheet
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
    SearchScreenContent(
        searchUiState = viewModel.searchUiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    searchUiState: SearchUiState
) {
    val searchResultList by searchUiState.searchResultList.collectAsState()
    val isLoading by searchUiState.isLoading.collectAsState()
    val searchValue by searchUiState.searchValue.collectAsState()
    val currentItemDetail by searchUiState.currentItemDetail.collectAsState()
    val bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState)
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchValue,
                onValueChange = {value ->
                    searchUiState.onSearchQueryChange(value)
                    coroutineScope.launch {
                        if(scaffoldState.bottomSheetState.isVisible){
                            scaffoldState.bottomSheetState.hide()
                        }
                    }
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                }
            )
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                LoadingScreen()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (searchResultList.isEmpty()) {
                        item { NoResult() }
                    } else {
                        searchResultList.forEach { searchResult ->
                            item {
                                SearchItemRow(
                                    searchResult, searchUiState.saveFavorite
                                ) { searchItem ->
                                    searchUiState.updateDetails(searchItem)
                                    coroutineScope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
            DetailsBottomSheet(currentItemDetail, scaffoldState)
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun NoResult() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.no_search_results))
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val searchUiState = SearchUiState(
        isLoading = MutableStateFlow(false),
        searchResultList = MutableStateFlow(emptyList()),
        searchValue = MutableStateFlow(""),
        currentItemDetail = MutableStateFlow(null),
        onSearchQueryChange = {},
        saveFavorite = {},
        updateDetails = {}
    )

    MovieAppTheme {
        SearchScreenContent(searchUiState = searchUiState)
    }
}
