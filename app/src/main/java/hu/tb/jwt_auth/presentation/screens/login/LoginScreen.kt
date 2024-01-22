package hu.tb.jwt_auth.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.jwt_auth.R
import hu.tb.jwt_auth.presentation.screens.login.components.MessageDialog

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    loginSuccess: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading || !uiState.errorMessage.isNullOrBlank()) {
        MessageDialog(
            errorText = uiState.errorMessage,
            confirmButton = {
                viewModel.onEvent(LoginViewModel.OnEvent.ClearError)
            }
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LoginViewModel.UiEvent.LoginSuccess -> loginSuccess()
            }
        }
    }

    LoginScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginViewModel.UiState,
    onEvent: (LoginViewModel.OnEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = uiState.userNameText,
            onValueChange = {
                onEvent(LoginViewModel.OnEvent.OnUserNameTextChange(it))
            },
            label = {
                Text(text = "Username")
            },
        )
        OutlinedTextField(
            value = uiState.passwordText,
            onValueChange = {
                onEvent(LoginViewModel.OnEvent.OnPasswordTextChange(it))
            },
            label = {
                Text(text = "Password")
            },
            trailingIcon = {
                IconButton(onClick = { onEvent(LoginViewModel.OnEvent.PasswordVisibilityChange) }) {
                    Icon(
                        painter = if (uiState.isPasswordVisible) painterResource(id = R.drawable.outline_visibility_24) else painterResource(
                            id = R.drawable.outline_visibility_off_24
                        ),
                        contentDescription = "Show hide password icon"
                    )
                }
            },
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Spacer(modifier = Modifier.height(22.dp))
        Button(
            onClick = { onEvent(LoginViewModel.OnEvent.LoginClick) },
            content = {
                Text(text = "Log in")
            },
            enabled = uiState.userNameText.isNotEmpty() && uiState.passwordText.isNotEmpty()
        )
    }
}

@Preview
@Composable
fun LoginScreenContentPreview() {
    LoginScreenContent(
        uiState = LoginViewModel.UiState(),
        onEvent = {}
    )
}