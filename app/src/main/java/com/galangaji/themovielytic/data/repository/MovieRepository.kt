package com.galangaji.themovielytic.data.repository

import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.data.entity.MovieResponse
import io.reactivex.Completable
import io.reactivex.Flowable

interface MovieRepository {
    fun getPopularMovie(): Flowable<MovieResponse>
    fun getTopRatedMovie(): Flowable<MovieResponse>
    fun getNowPlayingMovie(): Flowable<MovieResponse>
    fun getUpcomingMovie(): Flowable<MovieResponse>
    fun getDetailMovie(idMovie: Int): Flowable<Movie>
    fun getAllFavoriteMovies(): Flowable<List<Movie>>
    fun deleteFavoriteMovie(movie: Movie): Completable
    fun insertFavoriteMovie(movie: Movie): Completable

}