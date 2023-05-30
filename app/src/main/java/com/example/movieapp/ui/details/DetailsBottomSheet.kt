package com.example.movieapp.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.ui.models.DetailItem
import com.example.movieapp.utils.Constants.MOVIE_TEXT
import com.example.movieapp.utils.Constants.TYPE_TEXT
import com.example.movieapp.utils.Constants.YEAR_TEXT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsBottomSheet(
    item: DetailItem?,
    scaffoldState: BottomSheetScaffoldState
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(model = item?.poster, contentDescription = null)
                Text(text = "$MOVIE_TEXT: ${item?.title}")
                Text(text = "$YEAR_TEXT: ${item?.year}")
                Text(text = "$TYPE_TEXT: ${item?.type}")
            }
        }) {
    }
}
