package com.galangaji.themovielytic.abstraction.base

import com.galangaji.themovielytic.data.entity.Movie
import io.reactivex.Completable
import io.reactivex.Flowable

abstract class UseCase<T> {
    abstract fun getPopularMovie(): T
    abstract fun getUpcomingMovie(): T
    abstract fun getNowPlayingMovie(): T
    abstract fun getTopRatedMovie(): T
    abstract fun getDetailMovie(idMovie: Int): Flowable<Movie>
    abstract fun getAllFavoriteMovies(): Flowable<List<Movie>>
    abstract fun deleteFavoritesMovie(movie: Movie): Completable
    abstract fun insertFavoritesMovie(movie: Movie): Completable

}