package com.haroldcalayan.mubi.presentation.login_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.haroldcalayan.mubi.presentation.main_activity.MainActivity
import com.haroldcalayan.mubi.ui.theme.MubiTheme
import com.haroldcalayan.mubi.R.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        setContent {
            MubiTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(painter = painterResource(id = drawable.mubi_login_logo), contentDescription = "mubi logo")
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = getString(string.text_sign_in_login_screen),
                            style = MaterialTheme.typography.h6,
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                            ,onClick = {
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                        }) {
                            Text(
                                text = "LOGIN",

                            )
                        }
                    }
                }
            }
        }
    }
}