package com.galangaji.themovielytic.data.presistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.galangaji.themovielytic.data.entity.Movie

@Database(entities = [Movie::class], version = 1)
abstract class FavoriteMovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        @Volatile
        private var INSTACE: FavoriteMovieDatabase? = null

        fun getInstance(context: Context): FavoriteMovieDatabase =
            INSTACE ?: synchronized(this) {
                INSTACE ?: buidDatabase(context).also { INSTACE = it }
            }

        private fun buidDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavoriteMovieDatabase::class.java, "favorite_movie.db"
            ).build()
    }


}