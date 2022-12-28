package com.haroldcalayan.mubi.di

import com.haroldcalayan.mubi.data.repository.AuthenticationRepository
import com.haroldcalayan.mubi.data.repository.AuthenticationRepositoryImpl
import com.haroldcalayan.mubi.data.repository.MovieRepository
import com.haroldcalayan.mubi.data.repository.MovieRepositoryImpl
import com.haroldcalayan.mubi.data.source.remote.TMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(api: TMDBApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(api: TMDBApi): AuthenticationRepository {
        return AuthenticationRepositoryImpl(api)
    }
}