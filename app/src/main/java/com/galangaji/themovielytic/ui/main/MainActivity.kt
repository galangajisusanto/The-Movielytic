package com.galangaji.themovielytic.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.galangaji.themovielytic.R
import com.galangaji.themovielytic.abstraction.state.LoaderState
import com.galangaji.themovielytic.abstraction.util.showToast
import com.galangaji.themovielytic.abstraction.util.viewModelProvider
import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.di.DaggerMainComponent
import com.galangaji.themovielytic.di.module.PopularMovieModule
import com.galangaji.themovielytic.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieViewModel
    private val movies = mutableListOf<Movie>()
    private lateinit var _adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initInjector()

        viewModel = viewModelProvider(viewModelFactory)
        viewModel.getUpcomingMovie()

        initView()
        initObservable()
    }

    private fun initObservable() {
        viewModel.error.observe(this, Observer {
            showToast(it)
        })

        viewModel.state.observe(this, Observer {
            when (it) {
                is LoaderState.ShowLoading -> {
                    showToast("Loading...")
                }
                is LoaderState.HideLoading -> {
                    showToast("Complete!")
                }
            }
        })

        viewModel.movies.observe(this, Observer {
            movies.clear()
            movies.addAll(it.results)
            _adapter.notifyDataSetChanged()
        })
    }

    private fun initView() {
        _adapter = MovieAdapter(movies)
        lstMovies.apply {
            layoutManager = GridLayoutManager(
                this@MainActivity,
                2
            )
            adapter = _adapter
        }
    }

    private fun initInjector() {
        DaggerMainComponent
            .builder()
            .popularMovieModule(PopularMovieModule())
            .build()
            .inject(this)
    }
}
