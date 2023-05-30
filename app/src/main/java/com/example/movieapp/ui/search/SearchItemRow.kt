package com.example.movieapp.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.ui.models.SearchItem

@Composable
fun SearchItemRow(
    searchResult: SearchItem,
    onFavoriteClicked: (SearchItem) -> Unit,
    updateDetails: (SearchItem) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth().clickable { updateDetails(searchResult) },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)

    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp),
        ) {
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = searchResult.poster,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(2.5f),

                ) {
                Text(
                    text = "${searchResult.title} (${searchResult.type})",
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    softWrap = true
                )
                Text(
                    text = searchResult.year
                )
            }
            Icon(
                modifier = Modifier
                    .padding(top = 16.dp, end = 8.dp)
                    .weight(0.5f)
                    .clickable { onFavoriteClicked(searchResult) },
                painter = painterResource(
                    if (searchResult.isFavorite) R.drawable.ic_start_fill else R.drawable.ic_start_empty
                ),
                contentDescription = null
            )
        }
    }
}
