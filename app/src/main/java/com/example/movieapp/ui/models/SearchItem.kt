package com.example.movieapp.ui.models

import com.example.movieapp.data.database.models.FavoriteItem
import com.example.movieapp.utils.ToDataBaseMapper
import com.example.movieapp.utils.UiModelIntegrationMapper

data class SearchItem(
    val title: String,
    val year: String,
    val id: String,
    val type: String,
    val poster: String,
    val isFavorite: Boolean
) : ToDataBaseMapper<FavoriteItem>, UiModelIntegrationMapper<DetailItem> {
    override fun mapToDataBaseModel(): FavoriteItem {
        return FavoriteItem(itemId = id, title = title, poster = poster, type = type, year = year)
    }

    override fun mapToUiModel(): DetailItem {
        return DetailItem(
            title = title,
            year = year,
            type = type,
            poster = poster
        )
    }
}
