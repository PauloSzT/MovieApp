package com.example.movieapp.data.network.models

import com.example.movieapp.utils.Constants.SEARCH_TITLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchItemCollection(
    @SerialName(SEARCH_TITLE)
    val collection: List<RemoteSearchItem>? = emptyList()
)
