package com.haroldcalayan.starships.di

import com.haroldcalayan.starships.data.repository.StarwarsRepository
import com.haroldcalayan.starships.data.repository.StarwarsRepositoryImpl
import com.haroldcalayan.starships.data.source.remote.service.StarwarsApiService
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
    fun provideStarwarsRepository(api: StarwarsApiService): StarwarsRepository {
        return StarwarsRepositoryImpl(api)
    }
}