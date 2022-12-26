package com.haroldcalayan.mubi.presentation.main_activity.movie_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.common.Constants
import com.haroldcalayan.mubi.common.Constants.CATEGORY_AIRING_TODAY
import com.haroldcalayan.mubi.common.Constants.CATEGORY_ON_TV
import com.haroldcalayan.mubi.common.Constants.CATEGORY_POPULAR
import com.haroldcalayan.mubi.common.Constants.CATEGORY_TOP_RATED
import com.haroldcalayan.mubi.presentation.main_activity.Screen
import com.haroldcalayan.mubi.R

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MovieListScreen(
    navController: NavController,
    movieListViewModel: MovieListViewModel = hiltViewModel()
) {
    val state = movieListViewModel.state
    val categories =
        listOf(CATEGORY_POPULAR, CATEGORY_TOP_RATED, CATEGORY_ON_TV, CATEGORY_AIRING_TODAY)

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(categories) { categoryName ->
                Box(
                    modifier = Modifier.padding(10.dp)
                ) {
                    ClickableText(
                        text = AnnotatedString(categoryName), style = TextStyle(
                            color = Color.Black, fontSize = 12.sp
                        ),
                        onClick = {
                            when (categoryName) {
                                CATEGORY_POPULAR -> movieListViewModel.getPopularMovies()
                                CATEGORY_TOP_RATED -> movieListViewModel.getTopRatedMovies()
                                CATEGORY_ON_TV -> movieListViewModel.getOnTvMovies()
                                CATEGORY_AIRING_TODAY -> movieListViewModel.getAiringToday()
                            }
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            state = rememberLazyListState(), cells = GridCells.Fixed(2)
        ) {
            items(state.value.popularMovies?.results ?: emptyList()) { movie ->

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(15.dp),
                        elevation = 5.dp,
                        onClick = {
                            if (movie.releaseDate != null) {
                                navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                            } else {
                                navController.navigate(Screen.TVDetailScreen.route + "/${movie.id}")
                            }

                        }
                    ) {
                        Box(modifier = Modifier.wrapContentSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_play_circle_24),
                                contentDescription = "empty",
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent, Color.Black
                                            ), startY = 300f
                                        )
                                    )
                                    .fillMaxSize()
                            ) {
                                Image(
                                    painter = rememberCoilPainter(request = Constants.BASE_IMAGE_URL + movie.posterPath),
                                    contentDescription = "Movie"
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    contentAlignment = Alignment.BottomStart
                                ) {
                                    movie.title?.let { it1 ->
                                        Text(
                                            text = it1, style = TextStyle(
                                                color = Color.White, fontSize = 16.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}