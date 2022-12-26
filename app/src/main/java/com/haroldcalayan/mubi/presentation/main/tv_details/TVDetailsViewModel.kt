package com.haroldcalayan.mubi.presentation.main_activity.tv_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.domain.use_case.GetTVDetailUseCase
import com.haroldcalayan.mubi.presentation.main_activity.state.TVDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TVDetailsViewModel @Inject constructor(
    private val tvDetailsUseCase: GetTVDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(TVDetailState())
    val state: State<TVDetailState> = _state

    init {
        savedStateHandle.get<String>("tvId")?.let { movieId ->
            getMovie(movieId)
        }
    }

    private fun getMovie(tvId: String) {
        tvDetailsUseCase(tvId.toInt()).onEach { result ->
            when (result) {
                is Response.Success -> {
                    _state.value = TVDetailState(tv = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }


}