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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.R

@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
    SearchScreenContent(
        searchUiState = viewModel.searchUiState
    )
}

@Composable
fun SearchScreenContent(
    searchUiState: SearchUiState
) {
    val searchResultList by searchUiState.searchResultList.collectAsState()
    val isLoading by searchUiState.isLoading.collectAsState()
    val searchValue by searchUiState.searchValue.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row (){
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchValue ,
                onValueChange = searchUiState.onSearchQueryChange,
                trailingIcon = { Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )}
            )
        }
        if (isLoading) {
            LoadingScreen()
        } else {
            LazyColumn {
                if(searchResultList.isEmpty()){
                   item { NoResult() }
                }else{
                    searchResultList.forEach { searchResult ->
                        item {
                            SearchItemRow(searchResult)
                        }
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
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
fun NoResult(){
    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "No Results...")
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
}
