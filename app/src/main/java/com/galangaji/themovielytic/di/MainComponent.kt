package com.galangaji.themovielytic.di

import com.galangaji.themovielytic.di.module.PopularMovieModule
import com.galangaji.themovielytic.di.module.PopularMovieViewModelModule
import com.galangaji.themovielytic.ui.main.MainActivity
import dagger.Component

@MovieScope
@Component(modules = [
    PopularMovieModule::class,
    PopularMovieViewModelModule::class
])
interface MainComponent {
    fun inject(activity: MainActivity)
}