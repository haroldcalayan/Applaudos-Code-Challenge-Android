package com.haroldcalayan.mubi.presentation.main.movie_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.BuildConfig

@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
)  {
    val state = movieDetailsViewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = rememberCoilPainter(request = BuildConfig.BASE_IMAGE_URL + state.value.movie?.backdropPath),
            contentDescription = "Movie"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Title: ${state.value.movie?.title}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Summary",
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.h6,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${state.value.movie?.overview}"
        )
        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "Release Date: ${state.value.movie?.releaseDate}"
        )

        Text(
            text = "Ratings: ${state.value.movie?.voteAverage}"
        )
    }

}