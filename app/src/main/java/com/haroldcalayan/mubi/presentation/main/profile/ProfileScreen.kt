package com.haroldcalayan.mubi.presentation.main.profile

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.presentation.login.LoginActivity
import com.haroldcalayan.mubi.presentation.main.MainActivity
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun ProfileScreen(
    activity: MainActivity,
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val accountState = profileViewModel.accountState.value
    val favoriteTVShowsState = profileViewModel.favoriteTVShowsState.value

    val openDialog = remember {
        mutableStateOf(false)
    }

    accountState.data?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    Text(text = "Profile")
                },
                backgroundColor = colorResource(id = R.color.primaryDark),
                contentColor = colorResource(id = R.color.white),
                elevation = 12.dp,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.size(100.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = rememberCoilPainter(BuildConfig.BASE_IMAGE_URL + it?.avatar?.tmdb?.avatarPath),
                            contentDescription = "mubi logo",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(10.dp, Color("#e2e1f6".toColorInt()), CircleShape),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 10.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "Edit",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = it?.name ?: "",
                    style = MaterialTheme.typography.h6,
                )
                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "My Favorites",
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.h6,
                        color = Color.Blue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        state = rememberLazyListState(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                    ) {
                        items(
                            favoriteTVShowsState.popularMovies?.results ?: emptyList()
                        ) { favorites ->
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(8.dp)
                            ) {
                                Card(
                                    modifier = Modifier.wrapContentSize(),
                                    shape = RoundedCornerShape(15.dp),
                                    elevation = 5.dp
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.White),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = rememberCoilPainter(request = BuildConfig.BASE_IMAGE_URL + favorites.posterPath),
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
                                            favorites.originalName?.let {
                                                Text(
                                                    text = it,
                                                    style = TextStyle(
                                                        color = Color.Black,
                                                        fontSize = 16.sp
                                                    ),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }

                                            val rate by remember {
                                                mutableStateOf(
                                                    BigDecimal(
                                                        5 * ((favorites.voteAverage ?: 0.0) / 10.0)
                                                    ).setScale(1, RoundingMode.HALF_EVEN).toDouble()
                                                )
                                            }

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                val maxRate = rate.toInt()
                                                val unfilledStars =
                                                    (5 - kotlin.math.ceil(rate)).toInt()
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

                Spacer(modifier = Modifier.height(10.dp))

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                    onClick = {
                        openDialog.value = true
                    }) {
                    Text(text = "Log out")
                }
            }
        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { },
                dismissButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                    })
                    { Text(text = stringResource(R.string.alert_dialog_stay_message)) }
                },
                confirmButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                        profileViewModel.logout()
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                        activity.finishAffinity()
                    })
                    { Text(text = stringResource(R.string.alert_dialog_leave_message)) }
                },
                text = { Text(text = stringResource(R.string.alert_dialog_logout_message)) }
            )
        }
    }
}
