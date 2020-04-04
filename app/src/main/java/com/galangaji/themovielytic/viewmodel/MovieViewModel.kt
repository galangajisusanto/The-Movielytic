package com.galangaji.themovielytic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.galangaji.themovielytic.abstraction.base.BaseViewModel
import com.galangaji.themovielytic.abstraction.state.LoaderState
import com.galangaji.themovielytic.abstraction.util.rx.SchedulerProvider
import com.galangaji.themovielytic.data.domain.MovieUseCase
import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.data.entity.MovieResponse
import com.galangaji.themovielytic.data.entity.ReviewResponse
import javax.inject.Inject

interface MovieContract {
    fun onErrorPopularMovie(throwable: Throwable)
    fun getPopularMovie()

    fun onErrorUpcomingMovie(throwable: Throwable)
    fun getUpcomingMovie()

    fun onErrorTopRatedMovie(throwable: Throwable)
    fun getTopRatedMovie()

    fun onErrorNowPlayingMovie(throwable: Throwable)
    fun getNowPlayingMovie()

    fun onErrorDetailMovie(throwable: Throwable)
    fun getDetailMovie(idMovie: Int)

    fun onErrorGetAllFavoriteMovie(throwable: Throwable)
    fun getAllFavoriteMovie()

    fun onErrorDeleteFavoriteMovie(throwable: Throwable)
    fun deleteFavoriteMovie(movie: Movie)

    fun onErrorInsertFavoriteMovie(throwable: Throwable)
    fun insertFavoriteMovie(movie: Movie)

    fun onErrorGetReviewMovie(throwable: Throwable)
    fun getReviewMovie(idMovie: Int)

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

    private val _detailMovie = MutableLiveData<Movie>()
    val detailMovie: LiveData<Movie>
        get() = _detailMovie

    private val _favoriteMovies = MutableLiveData<List<Movie>>()
    val favoriteMovies: LiveData<List<Movie>>
        get() = _favoriteMovies

    private val _reviews = MutableLiveData<ReviewResponse>()
    val reviews: LiveData<ReviewResponse>
        get() = _reviews


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
            .doOnError { onErrorUpcomingMovie(it) }
            .subscribe {
                hideLoading()
                _movies.postValue(it)
            }
        )
    }

    override fun onErrorTopRatedMovie(throwable: Throwable) {
        _error.postValue(throwable.message)
        hideLoading()
    }

    override fun getTopRatedMovie() {
        subscribe(useCase.getTopRatedMovie()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { showLoading() }
            .doOnError { onErrorTopRatedMovie(it) }
            .subscribe {
                hideLoading()
                _movies.postValue(it)
            }
        )
    }

    override fun onErrorNowPlayingMovie(throwable: Throwable) {
        _error.postValue(throwable.message)
        hideLoading()
    }

    override fun getNowPlayingMovie() {
        subscribe(useCase.getNowPlayingMovie()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { showLoading() }
            .doOnError { onErrorNowPlayingMovie(it) }
            .subscribe {
                hideLoading()
                _movies.postValue(it)
            }
        )
    }

    override fun onErrorDetailMovie(throwable: Throwable) {
        _error.postValue(throwable.message)
        hideLoading()
    }

    override fun getDetailMovie(idMovie: Int) {
        subscribe(useCase.getDetailMovie(idMovie)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { showLoading() }
            .doOnError { onErrorDetailMovie(it) }
            .subscribe {
                hideLoading()
                _detailMovie.postValue(it)
            }
        )
    }

    override fun onErrorGetAllFavoriteMovie(throwable: Throwable) {
        _error.postValue(throwable.message)
        hideLoading()
    }

    override fun getAllFavoriteMovie() {
        subscribe((useCase.getAllFavoriteMovies())
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { showLoading() }
            .doOnError { onErrorPopularMovie(it) }
            .subscribe {
                hideLoading()
                _favoriteMovies.postValue(it)
            }
        )
    }

    override fun onErrorDeleteFavoriteMovie(throwable: Throwable) {
        _error.postValue(throwable.message)
        hideLoading()
    }

    override fun deleteFavoriteMovie(movie: Movie) {
        subscribe((useCase.deleteFavoritesMovie(movie))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { showLoading() }
            .doOnError { onErrorDeleteFavoriteMovie(it) }
            .subscribe {
                hideLoading()
            }
        )
    }

    override fun onErrorInsertFavoriteMovie(throwable: Throwable) {
        _error.postValue(throwable.message)
        hideLoading()
    }

    override fun insertFavoriteMovie(movie: Movie) {
        subscribe((useCase.insertFavoritesMovie(movie))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { showLoading() }
            .doOnError { onErrorDeleteFavoriteMovie(it) }
            .subscribe {
                hideLoading()
            }
        )
    }

    override fun onErrorGetReviewMovie(throwable: Throwable) {
        _error.postValue(throwable.message)
        hideLoading()
    }

    override fun getReviewMovie(idMovie: Int) {
        subscribe(useCase.getReviewMovies(idMovie)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { showLoading() }
            .doOnError { onErrorGetReviewMovie(it) }
            .subscribe {
                hideLoading()
                _reviews.postValue(it)
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