package com.haroldcalayan.mubi.presentation.approvedToken

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.common.Constants
import com.haroldcalayan.mubi.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class ApprovedTokenActivity : ComponentActivity() {

    private val viewModel: ApprovedTokenViewModel by viewModels()
    private lateinit var requestToken: String
    private lateinit var prefs: SharedPreferences

    private inner class ApprovedTokenWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            if (url.contains("/allow")) {
                viewModel.getRequestToken(requestToken)
            } else if (url.contains("/deny")) {
                onBackPressed()
            }
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        subscribeState()
        setContent {
            requestToken = intent.getStringExtra(KEY_REQUEST_TOKEN).orEmpty()
            MainContent(requestToken)
        }
    }

    private fun subscribeState() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.collectLatest { it ->
                it.data?.sessionID?.let { sessionId ->
                    prefs.edit().putString(Constants.SESSION_ID, sessionId).apply()

                    Timber.d("SESSION ID -> ${prefs.getString(Constants.SESSION_ID, "")}")
                    openMain()
                    finishAffinity()
                }
            }
        }
    }

    @Composable
    fun MainContent(requestToken: String) {
        Scaffold(
            content = { MyContent(requestToken) }
        )
    }

    @Composable
    fun MyContent(requestToken: String) {
        Timber.d("mUrl -> $requestToken")
        // Declare a string that contains a url
        val mUrl = "${BuildConfig.BASE_AUTH_URL}${requestToken}"

        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                webViewClient = ApprovedTokenWebViewClient()

                loadUrl(mUrl)
            }
        }, update = {
            it.loadUrl(mUrl)
        })
    }

    private fun openMain() {
        val intent = Intent(this@ApprovedTokenActivity, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val KEY_REQUEST_TOKEN = "key_request_token"

        fun newIntent(context: Context?, token: String): Intent =
            Intent(context, ApprovedTokenActivity::class.java).apply {
                putExtra(KEY_REQUEST_TOKEN, token)
            }
    }
}