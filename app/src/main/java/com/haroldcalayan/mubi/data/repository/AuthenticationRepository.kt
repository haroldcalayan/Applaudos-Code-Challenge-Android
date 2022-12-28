package com.haroldcalayan.mubi.data.repository

import com.haroldcalayan.mubi.common.base.BaseRepository
import com.haroldcalayan.mubi.data.source.remote.TMDBApi
import com.haroldcalayan.mubi.data.source.remote.dto.AccountDTO
import com.haroldcalayan.mubi.data.source.remote.dto.RequestTokenDTO
import com.haroldcalayan.mubi.data.source.remote.dto.SessionDTO
import okhttp3.RequestBody

interface AuthenticationRepository {
    suspend fun getRequestToken(): RequestTokenDTO
    suspend fun getSession(request: RequestBody): SessionDTO
    suspend fun getAccount(sessionId: String): AccountDTO
}

class AuthenticationRepositoryImpl(
    private val api: TMDBApi
) : BaseRepository(), AuthenticationRepository {

    override suspend fun getRequestToken(): RequestTokenDTO {
        return api.getRequestToken()
    }

    override suspend fun getSession(request: RequestBody): SessionDTO {
        return api.getSession(requestBody = request)
    }

    override suspend fun getAccount(sessiontId: String): AccountDTO {
        return api.getAccount(sessionId = sessiontId)
    }
}