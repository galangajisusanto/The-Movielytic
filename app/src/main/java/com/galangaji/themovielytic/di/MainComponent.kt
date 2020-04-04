package com.galangaji.themovielytic.di

import com.galangaji.themovielytic.di.module.MovieModule
import com.galangaji.themovielytic.di.module.MovieViewModelModule
import com.galangaji.themovielytic.di.module.RoomModule
import com.galangaji.themovielytic.ui.detail.DetailMovieActivity
import com.galangaji.themovielytic.ui.favorite.FavoriteMovieActivity
import com.galangaji.themovielytic.ui.main.MainActivity
import dagger.Component

@MovieScope
@Component(
    modules = [
        MovieModule::class,
        MovieViewModelModule::class,
        RoomModule::class
    ]
)
interface MainComponent {
    fun injectMain(activity: MainActivity)
    fun injectDetail(activity: DetailMovieActivity)
    fun injectFavorite(activity: FavoriteMovieActivity)
}
