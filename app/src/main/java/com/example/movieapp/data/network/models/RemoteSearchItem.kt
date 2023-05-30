package com.example.movieapp.data.network.models

import com.example.movieapp.ui.models.SearchItem
import com.example.movieapp.utils.ToUiMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteSearchItem(
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
) : ToUiMapper<SearchItem> {
    override fun mapToUiModel(ids: List<String>): SearchItem {
        return SearchItem(
            title = title,
            year = year,
            id = id,
            type = type,
            poster = poster,
            isFavorite = ids.contains(id)
        )
    }
}
