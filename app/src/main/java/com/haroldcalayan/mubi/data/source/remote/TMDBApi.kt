package com.haroldcalayan.mubi.data.remote

import com.haroldcalayan.mubi.BuildConfig
import com.heroappsdev.mubiapp.data.remote.dto.MovieDetailsDTO
import com.heroappsdev.mubiapp.data.remote.dto.MoviesDTO
import com.heroappsdev.mubiapp.data.remote.dto.TVDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("3/movie/popular?page=1")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY
    ): MoviesDTO

    @GET("3/movie/top_rated?page=1")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY
    ): MoviesDTO

    @GET("3/tv/on_the_air?page=1")
    suspend fun getOnTvMovies(
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY
    ): MoviesDTO

    @GET("3/tv/airing_today?page=1")
    suspend fun getAiringTodayMovies(
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY
    ): MoviesDTO

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY
    ): MovieDetailsDTO

    @GET("3/tv/{tv_id}")
    suspend fun getTVDetail(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY
    ): TVDetailsDTO
}