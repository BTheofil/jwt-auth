package hu.tb.jwt_auth.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.jwt_auth.data.repository.ExampleRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ExampleRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    data class UiState(
        val userNameText: String = "",
        val passwordText: String = "",
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed class OnEvent {
        data object LoginClick : OnEvent()
        data object PasswordVisibilityChange: OnEvent()
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
                    uiState.value.also {
                        repository.authenticate(it.userNameText, it.passwordText)
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