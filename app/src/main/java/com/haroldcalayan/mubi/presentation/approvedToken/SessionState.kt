package com.haroldcalayan.mubi.presentation.approvedToken

import com.haroldcalayan.mubi.data.source.remote.dto.SessionDTO

data class SessionState(
    val isLoading: Boolean = false,
    val data: SessionDTO? = null,
    val error: String = ""
)
