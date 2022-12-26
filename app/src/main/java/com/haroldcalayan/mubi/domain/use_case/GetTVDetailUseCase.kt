package com.heroappsdev.mubiapp.domain.use_case

import com.heroappsdev.mubiapp.common.Response
import com.heroappsdev.mubiapp.data.remote.dto.MovieDetailsDTO
import com.heroappsdev.mubiapp.data.remote.dto.MoviesDTO
import com.heroappsdev.mubiapp.data.remote.dto.TVDetailsDTO
import com.heroappsdev.mubiapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTVDetailUseCase@Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Response<TVDetailsDTO>> = flow {
        try {
            emit(Response.Loading<TVDetailsDTO>())
            val movies = repository.getTVDetails(movieId)
            emit(Response.Success<TVDetailsDTO>(movies))
        } catch (e: HttpException) {
            emit(Response.Error<TVDetailsDTO>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Response.Error<TVDetailsDTO>("Couldn't reach server. Check your internet connection"))
        }
    }
}