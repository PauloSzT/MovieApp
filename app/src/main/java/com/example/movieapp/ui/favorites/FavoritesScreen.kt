package com.example.movieapp.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel
){

    FavoritesScreenContent(viewModel.favoriteUiState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreenContent(
    favoriteUiState: FavoriteUiState
){
    val favoriteList by favoriteUiState.favoriteList.collectAsState()
    val favoriteListState = rememberPagerState { favoriteList.size }
    val windowWidth = LocalConfiguration.current.screenWidthDp.dp

    if(favoriteList.isNotEmpty()){
        HorizontalPager(
            state = favoriteListState,
            pageSize = PageSize.Fixed(100.dp),
            contentPadding = PaddingValues(horizontal = (windowWidth - 100.dp).div(2))
        ) {pageNumber ->
            FavoriteItemComponent(model = favoriteList[pageNumber].poster ?: "")
        }
    }
}

@Composable
fun FavoriteItemComponent (model: String){
    Box (
        modifier = Modifier
            .height(120.dp)
            .width(80.dp)
    ){
        AsyncImage(
            modifier = Modifier.fillMaxHeight(),
            model = model,
            contentDescription = null)
    }
}


@Preview
@Composable
fun FavoritesScreenPreview () {}
