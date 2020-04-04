package com.galangaji.themovielytic.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galangaji.themovielytic.R
import com.galangaji.themovielytic.abstraction.state.LoaderState
import com.galangaji.themovielytic.abstraction.util.load
import com.galangaji.themovielytic.abstraction.util.showToast
import com.galangaji.themovielytic.abstraction.util.viewModelProvider
import com.galangaji.themovielytic.data.entity.Genre
import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.di.DaggerMainComponent
import com.galangaji.themovielytic.di.module.PopularMovieModule
import com.galangaji.themovielytic.abstraction.util.DateUtils
import com.galangaji.themovielytic.di.module.RoomModule
import com.galangaji.themovielytic.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_detail.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailMovieActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieViewModel
    private var movie = Movie()

    companion object {
        private const val ID_MOVIE = "id_movie"

        @JvmStatic
        fun generateIntent(context: Context?, id: Int): Intent {
            return Intent(context, DetailMovieActivity::class.java).apply {
                putExtra(ID_MOVIE, id)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        val extras = intent.extras

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)




        initInjector()
        viewModel = viewModelProvider(viewModelFactory)

        if (extras != null) {
            val idMovie = extras.getInt(ID_MOVIE)
            viewModel.getDetailMovie(idMovie)
        }

        button_insert.setOnClickListener {
            viewModel.successInsertFavoriteMovie(movie)
        }

        button_delete.setOnClickListener {
            viewModel.successDeleteFavoriteMovie(movie)
        }
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

        viewModel.detailMovie.observe(this, Observer {
            this.movie = it
            updateUi(it)
            prepareRecGenres(it.genres)

        })
    }

    private fun updateUi(movie: Movie) {
        collapsing_toolbar.title = movie.originalTitle
        img_back_drop.load(movie.bannerUrl())
        img_poster.load(movie.posterUrl())
        val date = SimpleDateFormat(
            DateUtils.NORMAL_DATE_PATTERN,
            Locale.getDefault()
        ).parse(movie.releaseDate)
        txt_name.text = movie.originalTitle
        txt_date_rilis.text = DateUtils.format(date, DateUtils.FULL_DATE_PATTERN)
        rat_rating.rating = (movie.voteAverage / 2).toFloat()
        rat_rating.isEnabled = false
        txt_overview.text = movie.overview

    }

    private fun prepareRecGenres(genres: List<Genre>?) {
        val adapter = GenreAdapter()
        adapter.setGenre(genres)
        rv_genre.adapter = adapter
        rv_genre.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private fun initInjector() {
        DaggerMainComponent
            .builder()
            .roomModule(RoomModule(application))
            .popularMovieModule(PopularMovieModule())
            .build()
            .injectDetail(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
