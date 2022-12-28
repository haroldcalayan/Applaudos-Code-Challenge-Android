package com.haroldcalayan.mubi.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldcalayan.mubi.common.Constants
import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.domain.use_case.GetRequestTokenUseCase
import com.haroldcalayan.mubi.presentation.main.state.PopularMoviesState
import com.haroldcalayan.mubi.presentation.main.state.RequestTokenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val requestTokenUseCase: GetRequestTokenUseCase
) : ViewModel() {
    private val _state = mutableStateOf(PopularMoviesState())
    val state: State<PopularMoviesState> = _state

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _requestState = MutableStateFlow(RequestTokenState())
    val requestState = _requestState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(Constants.SPLASH_SCREEN_LIFE)
            _isLoading.value = false
        }
    }

    fun getRequestToken() {
        requestTokenUseCase().onEach { result ->
            when (result) {
                is Response.Success -> {
                    _requestState.value = RequestTokenState(data = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}