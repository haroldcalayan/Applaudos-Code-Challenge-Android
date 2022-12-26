package com.haroldcalayan.mubi.presentation.main_activity

sealed class Screen(val route: String) {
    object MovieListScreen : Screen("movie_list_screen")
    object MovieDetailScreen : Screen("movie_detail_screen")
    object TVDetailScreen : Screen("tv_detail_screen")
}