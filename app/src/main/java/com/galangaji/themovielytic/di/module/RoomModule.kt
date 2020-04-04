package com.galangaji.themovielytic.di.module

import android.app.Application
import androidx.room.Room
import com.galangaji.themovielytic.data.presistance.FavoriteMovieDao
import com.galangaji.themovielytic.data.presistance.FavoriteMovieDatabase
import com.galangaji.themovielytic.di.MovieScope
import dagger.Module
import dagger.Provides

import javax.inject.Singleton


@Module
class RoomModule {
    private var favoriteMovieDatabase: FavoriteMovieDatabase


    constructor(mApplication: Application) {
        favoriteMovieDatabase =
            Room.databaseBuilder<FavoriteMovieDatabase>(
                mApplication,
                FavoriteMovieDatabase::class.java,
                "favorite_movie.db"
            ).build()

    }

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