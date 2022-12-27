package com.haroldcalayan.mubi.presentation.main.movie_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.common.Constants.CATEGORY_AIRING_TODAY
import com.haroldcalayan.mubi.common.Constants.CATEGORY_ON_TV
import com.haroldcalayan.mubi.common.Constants.CATEGORY_POPULAR
import com.haroldcalayan.mubi.common.Constants.CATEGORY_TOP_RATED
import com.haroldcalayan.mubi.presentation.main.Screen
import java.math.BigDecimal
import java.math.RoundingMode

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

    var selectedOption by remember {
        mutableStateOf(CATEGORY_POPULAR)
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(categories) { categoryName ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = categoryName,
                        style = TextStyle(
                            color = if (selectedOption == categoryName) Color.White else Color.Black,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                onSelectionChange(categoryName)
                                when (categoryName) {
                                    CATEGORY_POPULAR -> movieListViewModel.getPopularMovies()
                                    CATEGORY_TOP_RATED -> movieListViewModel.getTopRatedMovies()
                                    CATEGORY_ON_TV -> movieListViewModel.getOnTvMovies()
                                    CATEGORY_AIRING_TODAY -> movieListViewModel.getAiringToday()
                                }
                            }
                            .background(
                                if (categoryName == selectedOption) {
                                    Color.Blue
                                } else {
                                    Color.LightGray
                                }
                            )
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            modifier = Modifier.weight(1f, true),
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
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = rememberCoilPainter(request = BuildConfig.BASE_IMAGE_URL + movie.posterPath),
                                contentDescription = "Movie",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .padding(8.dp)
                            ) {
                                movie.title?.let {
                                    Text(
                                        text = it,
                                        style = TextStyle(color = Color.Black, fontSize = 16.sp),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                val rate by remember {
                                    mutableStateOf(
                                        BigDecimal(
                                            5 * ((movie.voteAverage ?: 0.0) / 10.0)
                                        ).setScale(1, RoundingMode.HALF_EVEN).toDouble()
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    val maxRate = rate.toInt()
                                    val unfilledStars = (5 - kotlin.math.ceil(rate)).toInt()
                                    val halfStar = !(rate.rem(1).equals(0.0))

                                    repeat(maxRate) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_star_rate),
                                            contentDescription = "star",
                                            tint = Color(R.color.ic_star)
                                        )
                                    }

                                    if (halfStar) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_star_half),
                                            contentDescription = "star",
                                            tint = Color(R.color.ic_star)
                                        )
                                    }

                                    repeat(unfilledStars) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_star_border),
                                            contentDescription = "star",
                                            tint = Color(R.color.ic_star)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(2.dp))

                                    Text(
                                        text = rate.toString(),
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 15.sp
                                        ),
                                        maxLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

