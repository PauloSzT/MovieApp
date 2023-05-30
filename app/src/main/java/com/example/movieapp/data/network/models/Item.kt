package com.example.movieapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("Title")
    val title: String,
    @SerialName("Year")
    val year: String
)
