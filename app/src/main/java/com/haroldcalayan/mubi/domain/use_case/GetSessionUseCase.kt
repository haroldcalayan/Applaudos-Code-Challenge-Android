package com.haroldcalayan.mubi.domain.use_case

import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.data.repository.AuthenticationRepository
import com.haroldcalayan.mubi.data.repository.MovieRepository
import com.haroldcalayan.mubi.data.source.remote.dto.SessionDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(requestToken: String): Flow<Response<SessionDTO>> = flow {
        try {
            emit(Response.Loading())
            val json = JSONObject()
            json.put("request_token", requestToken)
            val data = repository.getSession(json.toString().toRequestBody("application/json".toMediaTypeOrNull()))
            emit(Response.Success(data))
        } catch (e: HttpException) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Response.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}