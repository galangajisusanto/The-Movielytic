package com.galangaji.themovielytic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.galangaji.themovielytic.abstraction.base.BaseViewModel
import com.galangaji.themovielytic.abstraction.state.LoaderState
import com.galangaji.themovielytic.abstraction.util.rx.SchedulerProvider
import com.galangaji.themovielytic.data.domain.MovieUseCase
import com.galangaji.themovielytic.data.entity.MovieResponse
import javax.inject.Inject

interface MovieContract {
    fun onErrorPopularMovie(throwable: Throwable)
    fun getPopularMovie()

    fun onErrorUpcomingMovie(throwable: Throwable)
    fun getUpcomingMovie()
}

class MovieViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel(), MovieContract {

    private val _movies = MutableLiveData<MovieResponse>()
    val movies: LiveData<MovieResponse>
        get() = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    override fun onErrorPopularMovie(throwable: Throwable) {
        _error.postValue(throwable.message)
        hideLoading()
    }

    override fun getPopularMovie() {
        subscribe(useCase.getPopularMovie()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { showLoading() }
            .doOnError { onErrorPopularMovie(it) }
            .subscribe {
                hideLoading()
                _movies.postValue(it)
            }
        )
    }

    override fun onErrorUpcomingMovie(throwable: Throwable) {
        _error.postValue(throwable.message)
        hideLoading()
    }

    override fun getUpcomingMovie() {
        subscribe(useCase.getUpcomingMovie()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { showLoading() }
            .doOnError { onErrorPopularMovie(it) }
            .subscribe {
                hideLoading()
                _movies.postValue(it)
            }
        )
    }

    private fun showLoading() {
        _state.postValue(LoaderState.ShowLoading)
    }

    private fun hideLoading() {
        _state.postValue(LoaderState.HideLoading)
    }
}