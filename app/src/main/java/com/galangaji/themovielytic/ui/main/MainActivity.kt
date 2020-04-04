package com.galangaji.themovielytic.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.galangaji.themovielytic.R
import com.galangaji.themovielytic.abstraction.state.LoaderState
import com.galangaji.themovielytic.abstraction.util.showToast
import com.galangaji.themovielytic.abstraction.util.viewModelProvider
import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.di.DaggerMainComponent
import com.galangaji.themovielytic.di.module.MovieModule
import com.galangaji.themovielytic.di.module.RoomModule
import com.galangaji.themovielytic.ui.favorite.FavoriteMovieActivity
import com.galangaji.themovielytic.viewmodel.MovieViewModel

import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CategoryListDialogFragment.categoryListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieViewModel
    private val movies = mutableListOf<Movie>()
    private lateinit var _adapter: MovieAdapter
    private lateinit var categoryDialog: CategoryListDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initInjector()

        viewModel = viewModelProvider(viewModelFactory)
        viewModel.getPopularMovie()

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
        setSupportActionBar(toolbar_main)
        toolbar_main.title = getString(R.string.app_name)
        _adapter = MovieAdapter(movies)
        lstMovies.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity
            )
            adapter = _adapter
        }
        categoryDialog = CategoryListDialogFragment.newInstance(4, this)

        btnChoseCategory.setOnClickListener {
            categoryDialog.show(supportFragmentManager, "category")
        }
    }

    private fun initInjector() {
        DaggerMainComponent
            .builder()
            .roomModule(RoomModule(application))
            .movieModule(MovieModule())
            .build()
            .injectMain(this)
    }

    override fun onClickNowPlaying() {
        viewModel.getNowPlayingMovie()
        categoryDialog.dismiss()
    }

    override fun onTopRated() {
        viewModel.getTopRatedMovie()
        categoryDialog.dismiss()
    }

    override fun onClickUpcoming() {
        viewModel.getUpcomingMovie()
        categoryDialog.dismiss()
    }

    override fun onClickPopular() {
        viewModel.getPopularMovie()
        categoryDialog.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_favorite) {
            startActivity(FavoriteMovieActivity.generateIntent(this))
        }
        return super.onOptionsItemSelected(item)
    }
}


