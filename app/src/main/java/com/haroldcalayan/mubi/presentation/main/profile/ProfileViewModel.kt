package com.haroldcalayan.mubi.presentation.main.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.domain.use_case.GetAccountDetailsUseCase
import com.haroldcalayan.mubi.domain.use_case.GetFavoriteTVShowsUseCase
import com.haroldcalayan.mubi.presentation.main.state.AccountState
import com.haroldcalayan.mubi.presentation.main.state.PopularMoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val getFavoriteTVShowsUseCase: GetFavoriteTVShowsUseCase
): ViewModel() {

    private val _accountState = mutableStateOf(AccountState())
    val accountState: State<AccountState> = _accountState

    private val _favoriteTVShowsState = mutableStateOf(PopularMoviesState())
    val favoriteTVShowsState: State<PopularMoviesState> = _favoriteTVShowsState

    fun getAccount(sessionId: String) {
        getAccountDetailsUseCase(sessionId).onEach { result ->
            when(result) {
                is Response.Success -> {
                    _accountState.value = AccountState(data = result.data)
                    getFavoriteTVShows(result.data?.id ?: 0, sessionId)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFavoriteTVShows(accountId: Int, sessionId: String) {
        getFavoriteTVShowsUseCase(accountId, sessionId).onEach { result ->
            when(result) {
                is Response.Success -> {
                    Timber.d("favorite tv shows: " + result.data)
                    _favoriteTVShowsState.value = PopularMoviesState(popularMovies = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}