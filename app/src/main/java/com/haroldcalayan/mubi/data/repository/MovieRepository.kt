package com.haroldcalayan.mubi.data.repository

import com.haroldcalayan.mubi.common.base.BaseRepository
import com.haroldcalayan.mubi.data.source.remote.TMDBApi
import com.haroldcalayan.mubi.data.source.remote.dto.*
import okhttp3.RequestBody

interface MovieRepository {
    suspend fun getPopularMovies(): MoviesDTO
    suspend fun getTopRatedMovies(): MoviesDTO
    suspend fun getOnTvMovies(): MoviesDTO
    suspend fun getAiringTodayMovies(): MoviesDTO
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO
    suspend fun getTVDetails(tvId: Int): TVDetailsDTO
    suspend fun getRequestToken(): RequestTokenDTO
    suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): SeasonDTO
    suspend fun searchMovie(query: String): SearchResultDTO
    suspend fun getSession(request: RequestBody): SessionDTO
    suspend fun getAccount(sessionId: String): AccountDTO
    suspend fun getFavoriteTVShows(accountId: Int, sessionId: String): MoviesDTO
}

class MovieRepositoryImpl(
    private val api: TMDBApi
) : BaseRepository(), MovieRepository {

    override suspend fun getRequestToken(): RequestTokenDTO {
        return api.getRequestToken()
    }

    override suspend fun getPopularMovies(): MoviesDTO {
        return api.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MoviesDTO {
        return api.getTopRatedMovies()
    }

    override suspend fun getOnTvMovies(): MoviesDTO {
        return api.getOnTvMovies()
    }

    override suspend fun getAiringTodayMovies(): MoviesDTO {
        return api.getAiringTodayMovies()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO {
        return api.getMovieDetail(movieId = movieId)
    }

    override suspend fun getTVDetails(tvId: Int): TVDetailsDTO {
        return api.getTVDetail(tvId = tvId)
    }

    override suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): SeasonDTO {
        return api.getSeasonDetail(tvId = tvId, seasonNumber = seasonNumber)
    }

    override suspend fun searchMovie(query: String): SearchResultDTO {
        return api.getSearch(query = query)
    }

    override suspend fun getSession(request: RequestBody): SessionDTO {
        return api.getSession(requestBody = request)
    }

    override suspend fun getAccount(sessiontId: String): AccountDTO {
        return api.getAccount(sessionId = sessiontId)
    }

    override suspend fun getFavoriteTVShows(accountId: Int, sessionId: String): MoviesDTO {
        return api.getFavoriteTVShows(accountId = accountId, sessionId = sessionId)
    }
}