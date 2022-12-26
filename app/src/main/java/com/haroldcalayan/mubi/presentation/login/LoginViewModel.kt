package com.haroldcalayan.mubi.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldcalayan.mubi.common.Constants
import com.haroldcalayan.mubi.presentation.main.state.PopularMoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(PopularMoviesState())
    val state: State<PopularMoviesState> = _state

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(Constants.SPLASH_SCREEN_LIFE)
            _isLoading.value = false
        }
    }

}