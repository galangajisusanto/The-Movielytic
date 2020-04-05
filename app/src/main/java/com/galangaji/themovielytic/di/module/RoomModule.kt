package com.galangaji.themovielytic.di.module

import android.app.Application
import androidx.room.Room
import com.galangaji.themovielytic.data.presistance.FavoriteMovieDao
import com.galangaji.themovielytic.data.presistance.FavoriteMovieDatabase
import com.galangaji.themovielytic.di.MovieScope
import dagger.Module
import dagger.Provides

@Module
class RoomModule(mApplication: Application) {
    private var favoriteMovieDatabase: FavoriteMovieDatabase = Room.databaseBuilder<FavoriteMovieDatabase>(
        mApplication,
        FavoriteMovieDatabase::class.java,
        "favorite_movie.db"
    ).build()

    @Provides
    @MovieScope
    fun providesRoomDatabase(): FavoriteMovieDatabase {
        return favoriteMovieDatabase
    }

    @Provides
    @MovieScope
    fun providesFavoriteMovieDao(favoriteMovieDatabase: FavoriteMovieDatabase): FavoriteMovieDao {
        return favoriteMovieDatabase.favoriteMovieDao()
    }


}