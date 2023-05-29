package com.example.movieapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchItemCollection(
    @SerialName("Search")
    val collection: List<RemoteSearchItem>? = emptyList()
)
