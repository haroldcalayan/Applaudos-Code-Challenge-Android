package com.haroldcalayan.mubi.presentation.main.profile

import android.content.Intent
import android.preference.PreferenceManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.common.Constants
import com.haroldcalayan.mubi.presentation.login.LoginActivity
import com.haroldcalayan.mubi.presentation.main.MainActivity

@Composable
fun ProfileScreen(
    activity: MainActivity,
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val sessionId = prefs.getString(Constants.PREF_KEY_SESSION_ID, null)
    val accountState = profileViewModel.accountState.value

    val openDialog = remember {
        mutableStateOf(false)
    }

    sessionId?.let {
        profileViewModel.getAccount(it)
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
                        prefs.edit().remove(Constants.PREF_KEY_SESSION_ID).apply();
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
