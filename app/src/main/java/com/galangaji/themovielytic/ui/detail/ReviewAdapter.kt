package com.galangaji.themovielytic.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.galangaji.themovielytic.R
import com.galangaji.themovielytic.abstraction.util.load
import com.galangaji.themovielytic.data.entity.Movie
import com.galangaji.themovielytic.ui.detail.DetailMovieActivity
import com.galangaji.themovielytic.abstraction.util.DateUtils
import com.galangaji.themovielytic.data.entity.Review
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReviewAdapter(
    private val reviews: List<Review>
) : RecyclerView.Adapter<ReviewAdapter.ReviewMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewMovieViewHolder.create(parent)

    override fun onBindViewHolder(holder: ReviewMovieViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int = reviews.size

    class ReviewMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(review: Review) {
            with(itemView) {
                tv_item_title.text = review.author
                tv_item_description.text = review.content
            }
        }

        private fun onMovieItemClick(movie: Movie) {
            with(itemView.context) {
                startActivity(DetailMovieActivity.generateIntent(this, movie.id))
            }
        }

        companion object {
            fun create(viewGroup: ViewGroup): ReviewMovieViewHolder {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.item_review, viewGroup, false)
                return ReviewMovieViewHolder(view)
            }
        }
    }

}