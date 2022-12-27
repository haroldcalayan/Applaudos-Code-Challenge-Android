package com.haroldcalayan.mubi.data.source.remote

import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.data.source.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("3/authentication/token/new")
    suspend fun getRequestToken(
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY
    ): RequestTokenDTO

    @GET("3/account")
    suspend fun getAccountId(
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY,
        @Query("session_id") sessionId: String
    ): RequestTokenDTO

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

    @GET("3/tv/{tv_id}/season/{season_number}")
    suspend fun getSeasonDetail(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY
    ): SeasonDTO

    @GET("/search/keyword")
    suspend fun getSearch(
        @Query("api_key") apiKey: String? = BuildConfig.BASE_APP_API_KEY,
        @Query("query") query: String
    ) : SearchResultDTO
}