package dev.renheyzer.memorize.feature.auth.presentation.ui.registration.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.asString
import dev.renheyzer.memorize.core.ui.component.MemorizeDefaultButton
import dev.renheyzer.memorize.core.ui.component.OutlinedErrorTextField
import dev.renheyzer.memorize.feature.auth.presentation.store.registration.RegistrationUiState
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun RegistrationContent(
    modifier: Modifier,
    uiState: RegistrationUiState,
    onSignUpClick: (email: String, password: String, confirmPassword: String) -> Unit,
    onAlreadyHaveAnAccountClick: () -> Unit,
) {
    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val isButtonEnabled by remember {
        derivedStateOf {
            email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        }
    }

    Column(modifier = modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        )

        Text(
            text = stringResource(R.string.registration),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        )

        OutlinedErrorTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
            },
            errorText = uiState.validationError.email?.asString(context),
            label = {
                Text(
                    text = stringResource(R.string.email),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = MaterialTheme.shapes.medium,
            enabled = !uiState.isLoading
        )

        OutlinedErrorTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
            },
            errorText = uiState.validationError.password?.asString(context),
            label = {
                Text(
                    text = stringResource(R.string.password),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = MaterialTheme.shapes.medium,
            enabled = !uiState.isLoading
        )

        OutlinedErrorTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            errorText = uiState.validationError.confirmPassword?.asString(context),
            label = {
                Text(
                    text = stringResource(R.string.confirm_password),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = MaterialTheme.shapes.medium,
            enabled = !uiState.isLoading
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        MemorizeDefaultButton(
            onClick = {
                onSignUpClick(email, password, confirmPassword)
            },
            text = stringResource(R.string.sign_up),
            enabled = isButtonEnabled && !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.clickable(enabled = !uiState.isLoading) { onAlreadyHaveAnAccountClick() },
            text = stringResource(R.string.already_have_an_account),
            color = MaterialTheme.colorScheme.secondaryContainer,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationContent() {
    MemorizeTheme {
        RegistrationContent(
            modifier = Modifier.fillMaxSize(),
            uiState = RegistrationUiState(),
            onSignUpClick = { _, _, _ -> },
            onAlreadyHaveAnAccountClick = {},
        )
    }
}