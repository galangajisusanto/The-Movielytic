package com.galangaji.themovielytic.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galangaji.themovielytic.abstraction.util.viewmodel.ViewModelFactory
import com.galangaji.themovielytic.abstraction.util.viewmodel.ViewModelKey
import com.galangaji.themovielytic.di.MovieScope
import com.galangaji.themovielytic.viewmodel.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class PopularMovieViewModelModule {

    @MovieScope
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel

}