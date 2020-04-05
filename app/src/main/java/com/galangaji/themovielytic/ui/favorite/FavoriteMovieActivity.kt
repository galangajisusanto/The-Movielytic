package com.galangaji.themovielytic.ui.favorite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.galangaji.themovielytic.R
import com.galangaji.themovielytic.abstraction.state.LoaderState
import com.galangaji.themovielytic.abstraction.util.showToast
import com.galangaji.themovielytic.abstraction.util.viewModelProvider
import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.di.DaggerMainComponent
import com.galangaji.themovielytic.di.module.MovieModule
import com.galangaji.themovielytic.di.module.RoomModule
import com.galangaji.themovielytic.ui.main.MovieAdapter
import com.galangaji.themovielytic.viewmodel.FavoriteMovieViewModel
import kotlinx.android.synthetic.main.activity_favorite_movie.*
import javax.inject.Inject

class FavoriteMovieActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavoriteMovieViewModel
    private val favoriteMovies = mutableListOf<Movie>()
    private lateinit var _adapter: MovieAdapter

    companion object {
        @JvmStatic
        fun generateIntent(context: Context?): Intent {
            return Intent(context, FavoriteMovieActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movie)

        initInjector()

        viewModel = viewModelProvider(viewModelFactory)
        viewModel.getAllFavoriteMovie()

        initView()
        initObservable()

    }

    private fun initView() {
        setSupportActionBar(toolbar_favorite)
        supportActionBar?.title = getString(R.string.favorite_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        _adapter = MovieAdapter(favoriteMovies)
        list_favorite.apply {
            layoutManager = LinearLayoutManager(
                this@FavoriteMovieActivity
            )
            adapter = _adapter
        }
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

        viewModel.favoriteMovies.observe(this, Observer {
            favoriteMovies.clear()
            favoriteMovies.addAll(it)
            _adapter.notifyDataSetChanged()
        })
    }

    private fun initInjector() {
        DaggerMainComponent
            .builder()
            .roomModule(RoomModule(application))
            .movieModule(MovieModule())
            .build()
            .injectFavorite(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        viewModel.getAllFavoriteMovie()
        super.onResume()
    }
}
