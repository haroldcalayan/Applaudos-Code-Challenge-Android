package com.haroldcalayan.mubi.presentation.main_activity.state

import com.heroappsdev.mubiapp.data.remote.dto.TVDetailsDTO

data class TVDetailState(
    val isLoading: Boolean = false,
    val tv: TVDetailsDTO? = null,
    val error: String = ""
)