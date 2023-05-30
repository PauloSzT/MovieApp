package com.example.movieapp.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.abs

@Composable
fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel
) {

    FavoritesScreenContent(viewModel.favoriteUiState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreenContent(
    favoriteUiState: FavoriteUiState
) {
    val favoriteList by favoriteUiState.favoriteList.collectAsState()
    val favoriteListState = rememberPagerState { favoriteList.size }
    val windowWidth = LocalConfiguration.current.screenWidthDp.dp

    if (favoriteList.isNotEmpty()) {
        HorizontalPager(
            modifier = Modifier.fillMaxHeight(),
            state = favoriteListState,
            pageSize = PageSize.Fixed(230.dp),
            contentPadding = PaddingValues(horizontal = (windowWidth - 180.dp).div(2))
        ) { pageNumber ->
            FavoriteItemComponent(
                modifier = Modifier.graphicsLayer {
                    val currentOffset =
                        abs(favoriteListState.calculateCurrentOffsetForPage(pageNumber))
                    scaleX = 1.5f - 0.5.times(currentOffset).toFloat()
                    scaleY = 1.5f - 0.5.times(currentOffset).toFloat()
                },
                model = favoriteList[pageNumber].poster ?: ""
            )
        }
    }else{
        NoFavoritesSaved()
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@Composable
fun FavoriteItemComponent(
    modifier: Modifier,
    model: String
) {
    Box(
        modifier = modifier
            .height(270.dp)
            .width(180.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxHeight(),
            model = model,
            contentDescription = null
        )
    }
}

@Composable
fun NoFavoritesSaved(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "No Favorites Saved")
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    val favoriteUiState = FavoriteUiState(
        favoriteList = MutableStateFlow(emptyList())
    )
    MovieAppTheme {
        FavoritesScreenContent(favoriteUiState = favoriteUiState)
    }
}
