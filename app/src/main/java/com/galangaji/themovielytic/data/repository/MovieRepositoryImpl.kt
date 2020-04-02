package com.galangaji.themovielytic.data.repository

import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.data.entity.MovieResponse
import com.galangaji.themovielytic.network.ApiService
import io.reactivex.Flowable
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MovieRepository {

    override fun getPopularMovie(): Flowable<MovieResponse> {
        return apiService.getPopularMovie()
    }

    override fun getTopRatedMovie(): Flowable<MovieResponse> {
        return apiService.getTopRatedMovie()
    }

    override fun getNowPlayingMovie(): Flowable<MovieResponse> {
        return apiService.getNowPlayingMovie()
    }

    override fun getUpcomingMovie(): Flowable<MovieResponse> {
        return apiService.getUpcomingMovie()
    }

    override fun getDetailMovie(idMovie: Int): Flowable<Movie> {
        return apiService.getDetailsMovie(idMovie)
    }
}