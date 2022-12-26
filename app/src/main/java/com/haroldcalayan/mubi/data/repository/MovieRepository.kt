package com.haroldcalayan.mubi.data.repository

import com.haroldcalayan.mubi.data.source.remote.TMDBApi
import com.heroappsdev.mubiapp.data.remote.dto.MovieDetailsDTO
import com.heroappsdev.mubiapp.data.remote.dto.MoviesDTO
import com.heroappsdev.mubiapp.data.remote.dto.TVDetailsDTO

interface MovieRepository {
    suspend fun getPopularMovies(): MoviesDTO
    suspend fun getTopRatedMovies(): MoviesDTO
    suspend fun getOnTvMovies(): MoviesDTO
    suspend fun getAiringTodayMovies(): MoviesDTO
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO
    suspend fun getTVDetails(tvId: Int): TVDetailsDTO
}

class MovieRepositoryImpl(
    private val api: TMDBApi
) : MovieRepository {
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
}