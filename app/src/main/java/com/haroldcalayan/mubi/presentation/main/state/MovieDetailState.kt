package com.haroldcalayan.mubi.presentation.main_activity.state

import com.heroappsdev.mubiapp.data.remote.dto.MovieDetailsDTO

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: MovieDetailsDTO? = null,
    val error: String = ""
)