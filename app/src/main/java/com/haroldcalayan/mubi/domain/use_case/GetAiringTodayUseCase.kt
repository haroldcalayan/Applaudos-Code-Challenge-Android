package com.heroappsdev.mubiapp.domain.use_case

import com.heroappsdev.mubiapp.common.Response
import com.heroappsdev.mubiapp.data.remote.dto.MoviesDTO
import com.heroappsdev.mubiapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAiringTodayUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Response<MoviesDTO>> = flow {
        try {
            emit(Response.Loading<MoviesDTO>())
            val movies = repository.getAiringTodayMovies()
            emit(Response.Success<MoviesDTO>(movies))
        } catch (e: HttpException) {
            emit(Response.Error<MoviesDTO>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Response.Error<MoviesDTO>("Couldn't reach server. Check your internet connection"))
        }
    }
}