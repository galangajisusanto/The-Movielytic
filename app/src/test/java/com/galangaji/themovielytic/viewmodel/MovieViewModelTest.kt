package com.galangaji.themovielytic.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.galangaji.themovielytic.abstraction.state.LoaderState
import com.galangaji.themovielytic.abstraction.util.rx.TestSchedulerProvider
import com.galangaji.themovielytic.data.domain.MovieUseCase
import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.data.entity.MovieResponse
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MovieViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var useCase: MovieUseCase
    private lateinit var viewModel: MovieViewModel

    @Mock
    lateinit var movieObservable: Observer<MovieResponse>
    @Mock
    lateinit var stateObservable: Observer<LoaderState>

    @Captor
    lateinit var movieCaptor: ArgumentCaptor<MovieResponse>
    @Captor
    lateinit var stateCaptor: ArgumentCaptor<LoaderState>

    private val returnValue = Flowable.just(moviesData)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val scheduler = TestSchedulerProvider()
        viewModel = MovieViewModel(useCase, scheduler)

        viewModel.movies.observeForever(movieObservable)
        viewModel.state.observeForever(stateObservable)

    }

    @Test
    fun `it should return response correctly`() {
        `when`(useCase.getPopularMovie()).thenReturn(returnValue)
        viewModel.getPopularMovie()
        verify(movieObservable, atLeastOnce()).onChanged(movieCaptor.capture())
        returnValue
            .test()
            .assertValue {
                it.results.first() == movieCaptor.value.results.first()
            }
    }

    @Test fun `state handling correctly`() {
        `when`(useCase.getPopularMovie()).thenReturn(returnValue)
        viewModel.getPopularMovie()
        verify(stateObservable, atLeastOnce()).onChanged(stateCaptor.capture())
        assert(LoaderState.ShowLoading == stateCaptor.allValues[0]) //first, loader is showing
        assert(LoaderState.HideLoading == stateCaptor.allValues[1]) //then, hide the loader
    }

    @Test fun `it should return empty movie`() {
        val returnEmptyValue = Flowable.just(MovieResponse(0,0,0,listOf()))
        `when`(useCase.getPopularMovie()).thenReturn(returnEmptyValue)
        viewModel.getPopularMovie()
        verify(movieObservable, atLeastOnce()).onChanged(movieCaptor.capture())
        assert(movieCaptor.value.results.isEmpty())
    }


    @After
    fun tearDown() {
        clearInvocations(useCase, movieObservable)

    }

    /* mock data */
    companion object {
        private val movies = listOf(
            Movie(
                0.0,
                0,
                false,
                "posterPath",
                0,
                false,
                "backdropPath",
                "originalLanguage",
                "originalTitle",
                listOf(),
                "title",
                0.0,
                "overview",
                "releaseDate",
                listOf()
            )
        )

        private val moviesData = MovieResponse(
            1,
            1,
            10,
            movies
        )
    }
}