package com.example.movieapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.data.database.models.FavoriteItem

@Database(entities = [FavoriteItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): FavoriteDAO
    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "item_database")
                    .build()
        }
    }
}
