package com.heroappsdev.mubiapp.domain.use_case

import com.heroappsdev.mubiapp.common.Response
import com.heroappsdev.mubiapp.data.remote.dto.MovieDetailsDTO
import com.heroappsdev.mubiapp.data.remote.dto.MoviesDTO
import com.heroappsdev.mubiapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailsUseCase@Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Response<MovieDetailsDTO>> = flow {
        try {
            emit(Response.Loading<MovieDetailsDTO>())
            val movies = repository.getMovieDetails(movieId)
            emit(Response.Success<MovieDetailsDTO>(movies))
        } catch (e: HttpException) {
            emit(Response.Error<MovieDetailsDTO>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Response.Error<MovieDetailsDTO>("Couldn't reach server. Check your internet connection"))
        }
    }
}