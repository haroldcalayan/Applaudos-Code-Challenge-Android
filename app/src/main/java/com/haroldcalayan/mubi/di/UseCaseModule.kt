package com.haroldcalayan.mubi.di

import com.haroldcalayan.mubi.data.repository.MovieRepository
import com.haroldcalayan.mubi.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAiringTodaysUseCase(repository: MovieRepository): GetAiringTodayUseCase {
        return GetAiringTodayUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(repository: MovieRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetOnTvMoviesUseCase(repository: MovieRepository): GetOnTvMoviesUseCase {
        return GetOnTvMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPopularMovieUseCase(repository: MovieRepository): GetPopularMovieUseCase {
        return GetPopularMovieUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTopRatedMoviesUseCase(repository: MovieRepository): GetTopRatedMoviesUseCase {
        return GetTopRatedMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTVDetailUseCase(repository: MovieRepository): GetTVDetailUseCase {
        return GetTVDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRequestTokenUseCase(repository: MovieRepository): GetRequestTokenUseCase {
        return GetRequestTokenUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSeasonDetailsUseCase(repository: MovieRepository): GetSeasonDetailsUseCase {
        return GetSeasonDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchResultUseCase(repository: MovieRepository): GetSearchResultUseCase {
        return GetSearchResultUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAccountDetailsUseCase(repository: MovieRepository): GetAccountDetailsUseCase {
        return GetAccountDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieUseCase(repository: MovieRepository): GetFavoriteMovieUseCase {
        return GetFavoriteMovieUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSessionUseCase(repository: MovieRepository): GetSessionUseCase {
        return GetSessionUseCase(repository)
    }
}