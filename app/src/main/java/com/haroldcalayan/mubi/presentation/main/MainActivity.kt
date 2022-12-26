package com.haroldcalayan.mubi.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.common.ui.theme.MubiTheme
import com.haroldcalayan.mubi.common.utils.showToastShort
import com.haroldcalayan.mubi.presentation.main.movie_details.MovieDetailsScreen
import com.haroldcalayan.mubi.presentation.main.movie_list.MovieListScreen
import com.haroldcalayan.mubi.presentation.main.tv_details.TVDetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MubiTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "TV Shows")
                            },
                            backgroundColor = colorResource(id = R.color.primaryDark),
                            contentColor = colorResource(id = R.color.white),
                            elevation = 12.dp,
                            actions = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(Icons.Rounded.Search, "Search")
                                }
                                IconButton(onClick = { openProfile() }) {
                                    Icon(Icons.Rounded.Person, "Profile")
                                }
                            }
                        )
                    }, content = {
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
                    })
            }
        }
    }

    private fun openProfile() {
        showToastShort("Open Profile")
    }
}






