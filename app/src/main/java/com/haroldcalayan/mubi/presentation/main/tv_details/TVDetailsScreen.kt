package com.haroldcalayan.mubi.presentation.main_activity.tv_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.common.Constants

@Composable
fun TVDetailsScreen(
    navController: NavController,
    tvDetailsViewModel: TVDetailsViewModel = hiltViewModel()
) {
    val state = tvDetailsViewModel.state

    Column {
        Image(
            painter = rememberCoilPainter(request = Constants.BASE_IMAGE_URL + state.value.tv?.posterPath),
            contentDescription = "Movie"
        )
        Text(
            text = "Title: ${state.value.tv?.name}"
        )

        Text(
            text = "Release Date: ${state.value.tv?.firstAirDate}"
        )
        Text(
            text = "Ratings: ${state.value.tv?.voteAverage}"
        )
    }

}