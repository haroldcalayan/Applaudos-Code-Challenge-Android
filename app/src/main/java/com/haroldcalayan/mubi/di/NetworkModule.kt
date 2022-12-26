package com.heroappsdev.mubiapp.di

import com.heroappsdev.mubiapp.common.Constants.BASE_URL
import com.heroappsdev.mubiapp.data.remote.TMDBApi
import com.heroappsdev.mubiapp.data.repository.MovieRepositoryImpl
import com.heroappsdev.mubiapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient(okHttpBuilder: OkHttpClient.Builder) = okHttpBuilder.build()

    @Provides
    fun provideHttpClientBuilder(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun provideTMDBApi(
        client: OkHttpClient
    ): TMDBApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .build()
            .create(TMDBApi::class.java)
    }

    @Provides
    fun provideTMDBRepository(api: TMDBApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }


}