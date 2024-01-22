package hu.tb.jwt_auth.presentation.screens.login.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MessageDialog(
    errorText: String? = null,
    confirmButton: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            if (!errorText.isNullOrEmpty()) {
                TextButton(onClick = { confirmButton() }, content = {
                    Text(text = "Okay")
                })
            }
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (!errorText.isNullOrEmpty()) {
                    Icon(imageVector = Icons.Rounded.Warning, contentDescription = "Error icon")
                } else {
                    CircularProgressIndicator()
                }
            }
        },
        text = {
            errorText?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = it
                )
            } ?: Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Log in is in progress"
            )
        }
    )
}

@Preview
@Composable
fun LoadingDialogPreview() {
    MessageDialog {}
}