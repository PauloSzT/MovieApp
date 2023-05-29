package com.example.movieapp.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteItem(

    @PrimaryKey
    val itemId: String,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "poster")
    val poster: String?
)
