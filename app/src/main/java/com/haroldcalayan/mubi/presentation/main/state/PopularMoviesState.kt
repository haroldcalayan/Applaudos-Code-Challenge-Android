package com.haroldcalayan.mubi.presentation.main_activity.state

import com.heroappsdev.mubiapp.data.remote.dto.MoviesDTO

data class PopularMoviesState(
    val isLoading: Boolean = false,
    val popularMovies: MoviesDTO? = null,
    val error: String = ""
)