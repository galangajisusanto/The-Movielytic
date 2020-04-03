package com.galangaji.themovielytic.data.presistance

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galangaji.themovielytic.data.entity.Movie
import io.reactivex.Completable
import io.reactivex.Flowable

interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: Movie): Completable

    @Query("SELECT * FROM favorites_movie")
    fun getAllFavoriteMovies(): Flowable<List<Movie>>

    @Delete
    fun deleteFavoriteMovie(movie: Movie): Completable
}