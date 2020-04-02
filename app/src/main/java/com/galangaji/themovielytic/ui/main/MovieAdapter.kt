package com.galangaji.themovielytic.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.galangaji.themovielytic.R
import com.galangaji.themovielytic.abstraction.util.load
import com.galangaji.themovielytic.abstraction.util.showToast
import com.galangaji.themovielytic.data.entity.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

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
        private val txtTitle = view.txtMovieTitle
        private val txtYear = view.txtYear
        private val imgPoster = view.imgPoster

        fun bind(movie: Movie) {
            imgPoster.load(movie.posterUrl())
            txtTitle.text = movie.title
            txtYear.text = movie.releaseDate

            itemView.setOnClickListener {
                onMovieItemClick(movie)
            }
        }

        private fun onMovieItemClick(movie: Movie) {
            itemView.context.showToast(movie.title)
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