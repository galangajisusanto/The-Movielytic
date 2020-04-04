package com.galangaji.themovielytic.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.galangaji.themovielytic.R
import com.galangaji.themovielytic.abstraction.util.load
import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.ui.detail.DetailMovieActivity
import com.galangaji.themovielytic.abstraction.util.DateUtils
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter(
    private val movies: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.PopularMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PopularMovieViewHolder.create(parent)

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    class PopularMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            with(itemView) {
                tv_item_title.text = movie.title
                tv_item_description.text = movie.overview
                val date = SimpleDateFormat(
                    DateUtils.NORMAL_DATE_PATTERN,
                    Locale.getDefault()
                ).parse(movie.releaseDate)
                tv_item_date.text = DateUtils.format(date, DateUtils.FULL_DATE_PATTERN)
                img_poster.load(movie.posterUrl())
            }

            itemView.setOnClickListener {
                onMovieItemClick(movie)
            }

        }

        private fun onMovieItemClick(movie: Movie) {
            with(itemView.context) {
                startActivity(DetailMovieActivity.generateIntent(this, movie.id))
            }
        }

        companion object {
            fun create(viewGroup: ViewGroup): PopularMovieViewHolder {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.item_movie, viewGroup, false)
                return PopularMovieViewHolder(view)
            }
        }
    }

}