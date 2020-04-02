package com.galangaji.themovielytic.data.domain

import com.galangaji.themovielytic.abstraction.base.UseCase
import com.galangaji.themovielytic.data.entity.MovieResponse
import com.galangaji.themovielytic.data.repository.MovieRepository
import io.reactivex.Flowable
import javax.inject.Inject

open class MovieUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<Flowable<MovieResponse>>() {

    override fun execute(): Flowable<MovieResponse> {
        return repository.getPopularMovie()
    }

    fun getPopularMovie(): Flowable<MovieResponse>{
        return repository.getPopularMovie()
    }

    fun getUpcomingMovie(): Flowable<MovieResponse>{
        return repository.getUpcomingMovie()
    }

}