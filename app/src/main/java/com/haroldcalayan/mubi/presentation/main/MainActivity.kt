package com.haroldcalayan.mubi.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haroldcalayan.mubi.presentation.main_activity.movie_details.MovieDetailsScreen
import com.haroldcalayan.mubi.presentation.main_activity.movie_list.MovieListScreen
import com.haroldcalayan.mubi.presentation.main_activity.tv_details.TVDetailsScreen
import com.haroldcalayan.mubi.ui.theme.MubiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MubiTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MovieListScreen.route
                    ) {
                        composable(
                            route = Screen.MovieListScreen.route
                        ) {
                            MovieListScreen(navController)
                        }
                        composable(
                            route = Screen.MovieDetailScreen.route + "/{movieId}"
                        ) {
                            MovieDetailsScreen(navController)
                        }
                        composable(
                            route = Screen.TVDetailScreen.route + "/{tvId}"
                        ) {
                            TVDetailsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}






