package com.galangaji.themovielytic.di.module

import com.galangaji.themovielytic.abstraction.util.rx.AppSchedulerProvider
import com.galangaji.themovielytic.abstraction.util.rx.SchedulerProvider
import com.galangaji.themovielytic.data.domain.MovieUseCase
import com.galangaji.themovielytic.data.presistance.FavoriteMovieDao
import com.galangaji.themovielytic.data.repository.MovieRepository
import com.galangaji.themovielytic.data.repository.MovieRepositoryImpl
import com.galangaji.themovielytic.di.MovieScope
import com.galangaji.themovielytic.network.ApiService
import com.galangaji.themovielytic.network.Network
import dagger.Module
import dagger.Provides
import retrofit2.create

@Module
class MovieModule {

    @Provides
    @MovieScope
    fun provideNetworkBuilder(): ApiService {
        return Network.builder().create()
    }

    @Provides
    @MovieScope
    fun provideMovieRepository(
        services: ApiService,
        favoriteMovieDao: FavoriteMovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(services, favoriteMovieDao)
    }

    @Provides
    @MovieScope
    fun provideMovieUseCase(repository: MovieRepository): MovieUseCase {
        return MovieUseCase(repository)
    }

    @Provides
    @MovieScope
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

}