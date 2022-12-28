package com.haroldcalayan.mubi.presentation.main.profile

import android.content.Intent
import android.preference.PreferenceManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val sessionId = prefs.getString(Constants.SESSION_ID,"")

    sessionId?.let { profileViewModel.getAccount(it) }

    val state = profileViewModel.state.value

    val openDialog = remember {
        mutableStateOf(false)
    }

    state.data.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberCoilPainter(BuildConfig.BASE_IMAGE_URL + it?.avatar?.tmdb?.avatarPath),
                contentDescription = "mubi logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(10.dp, Color("#e2e1f6".toColorInt()), CircleShape),
            )
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
                    prefs.edit().remove(Constants.SESSION_ID).apply();
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                    activity.finishAffinity()
                    openDialog.value = false
                })
                { Text(text = stringResource(R.string.alert_dialog_leave_message)) }
            },
            text = { Text(text = stringResource(R.string.alert_dialog_logout_message)) }
        )
    }

}