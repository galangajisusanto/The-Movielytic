package com.galangaji.themovielytic.data.entity


import android.os.Parcelable
import com.galangaji.themovielytic.BuildConfig
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val popularity: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0,
    val video: Boolean = false,
    @SerializedName("poster_path")
    val posterPath: String = "",
    val id: Int = 0,
    val adult: Boolean = false,
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_title")
    val originalTitle: String = "",
    @SerializedName("genre_ids")
    val genreIds: List<Int> = listOf(),
    val title: String = "",
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    val overview: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    val genres: List<Genre> = listOf()
) : Parcelable {
    fun bannerUrl() = "${BuildConfig.IMAGE_URL}$backdropPath"
    fun posterUrl() = "${BuildConfig.IMAGE_URL}$posterPath"
}

@Parcelize
data class Genre(
    val id: Int = 0,
    val name: String = ""
) : Parcelable

data class MovieResponse(
    val page: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    val results: List<Movie> = listOf()
)