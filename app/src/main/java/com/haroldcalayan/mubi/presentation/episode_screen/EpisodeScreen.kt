package com.haroldcalayan.mubi.presentation.episode_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.R.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EpisodeScreen(
    navController: NavController,
    episodeViewModel: EpisodeViewModel = hiltViewModel()
) {
    val state = episodeViewModel.state.value

    state.season?.episodes.let { episode ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                state = rememberLazyListState(),
                cells = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(episode ?: emptyList()) { season ->
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.wrapContentSize(),
                            shape = RoundedCornerShape(15.dp),
                            elevation = 5.dp,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = if(season.stillPath == null) painterResource(id = drawable.ic_launcher_foreground) else rememberCoilPainter(request = BuildConfig.BASE_IMAGE_URL + season.stillPath),
                                    contentDescription = "Movie",
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(125.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    season.name?.let {
                                        Text(
                                            text = it,
                                            fontStyle = FontStyle.Italic,
                                            style = MaterialTheme.typography.h6,
                                            color = Color.Black
                                        )
                                    }
                                    season.overview?.let {
                                        Text(
                                            text = it,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 12.sp
                                            ),
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    season.runtime?.let {
                                        Text(
                                            text = "$it",
                                            style = TextStyle(color = Color.Blue, fontSize = 16.sp),
                                            color = Color.Blue
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