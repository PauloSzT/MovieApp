package com.example.movieapp.data.network.models

import com.example.movieapp.data.database.models.FavoriteItem
import com.example.movieapp.utils.ToDataBaseMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchItem(
    @SerialName("Title")
    val title: String,
    @SerialName("Year")
    val year: String,
    @SerialName("imdbID")
    val id: String,
    @SerialName("Type")
    val type: String,
    @SerialName("Poster")
    val poster: String
): ToDataBaseMapper<FavoriteItem>{
    override fun mapToDataBaseModel(): FavoriteItem {
        return FavoriteItem(itemId = id, title = title,poster = poster)
    }
}
