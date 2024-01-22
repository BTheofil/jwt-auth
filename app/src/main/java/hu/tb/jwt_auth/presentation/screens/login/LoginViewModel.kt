package hu.tb.jwt_auth.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.jwt_auth.data.DataStoreManager
import hu.tb.jwt_auth.domain.repository.ExampleRepository
import hu.tb.jwt_auth.domain.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ExampleRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    data class UiState(
        val userNameText: String = "",
        val passwordText: String = "",
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed class UiEvent {
        data object LoginSuccess : UiEvent()
        data class ShowPopup(val message: String) : UiEvent()
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            dataStore.getUser.collect { result ->
                if (result.username.isNotBlank() && result.password.isNotBlank()) {
                    _uiState.update {
                        it.copy(
                            userNameText = result.username,
                            passwordText = result.password
                        )
                    }
                    onEvent(OnEvent.LoginClick)
                }
            }
        }
    }

    sealed class OnEvent {
        data object LoginClick : OnEvent()
        data object PasswordVisibilityChange : OnEvent()
        data class OnUserNameTextChange(val text: String) : OnEvent()
        data class OnPasswordTextChange(val text: String) : OnEvent()
    }

    fun onEvent(event: OnEvent) {
        when (event) {
            OnEvent.LoginClick -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }

                    when (val result = repository.authenticate(
                        uiState.value.userNameText,
                        uiState.value.passwordText
                    )) {
                        is Resource.Success -> {

                            dataStore.setUsername(uiState.value.userNameText)
                            dataStore.setPassword(uiState.value.passwordText)

                            _uiEvent.send(UiEvent.LoginSuccess)
                        }

                        is Resource.Error -> {
                            _uiEvent.send(UiEvent.ShowPopup(result.message!!))
                        }
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }

            OnEvent.PasswordVisibilityChange -> {
                _uiState.update {
                    it.copy(
                        isPasswordVisible = !uiState.value.isPasswordVisible
                    )
                }
            }

            is OnEvent.OnUserNameTextChange -> _uiState.update {
                it.copy(userNameText = event.text)
            }


            is OnEvent.OnPasswordTextChange -> _uiState.update {
                it.copy(passwordText = event.text)
            }

        }
    }
}