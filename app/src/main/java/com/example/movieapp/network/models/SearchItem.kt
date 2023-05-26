package com.example.movieapp.network.models

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
)
