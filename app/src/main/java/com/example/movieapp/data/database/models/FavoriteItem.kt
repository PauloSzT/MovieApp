package com.example.movieapp.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.ui.models.DetailItem
import com.example.movieapp.utils.UiModelIntegrationMapper

@Entity(tableName = "favorite")
data class FavoriteItem(

    @PrimaryKey
    val itemId: String,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "poster")
    val poster: String?,

    @ColumnInfo(name = "year")
    val year: String?,

    @ColumnInfo(name = "type")
    val type: String?,

): UiModelIntegrationMapper<DetailItem>{
    override fun mapToUiModel(): DetailItem {
        return DetailItem(
            title = title ?: "Unknown",
            year = year ?: "Undefined",
            type = type ?: "Unknown",
            poster = poster ?: "")
    }
}
