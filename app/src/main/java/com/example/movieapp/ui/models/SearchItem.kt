package com.example.movieapp.ui.models

import com.example.movieapp.data.database.models.FavoriteItem
import com.example.movieapp.utils.ToDataBaseMapper

data class SearchItem(
    val title: String,
    val year: String,
    val id: String,
    val type: String,
    val poster: String,
    val isFavorite: Boolean
) : ToDataBaseMapper<FavoriteItem> {
    override fun mapToDataBaseModel(): FavoriteItem {
        return FavoriteItem(itemId = id, title = title, poster = poster)
    }
}
