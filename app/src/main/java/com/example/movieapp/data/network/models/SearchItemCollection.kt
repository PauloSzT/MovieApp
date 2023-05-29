package com.example.movieapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class SearchItemCollection(
    @SerialName("Search")
    val collection: List<SearchItem>? = emptyList()
)
