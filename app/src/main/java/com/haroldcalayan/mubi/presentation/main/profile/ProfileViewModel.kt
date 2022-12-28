package com.haroldcalayan.mubi.presentation.main.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.domain.use_case.GetAccountDetailsUseCase
import com.haroldcalayan.mubi.presentation.main.state.AccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
): ViewModel() {

    private val _state = mutableStateOf(AccountState())
    val state: State<AccountState> = _state

    fun getAccount(sessionId: String) {
        getAccountDetailsUseCase(sessionId).onEach { result ->
            when(result) {
                is Response.Success -> {
                    _state.value = AccountState(data = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}