package com.example.mobdevproject.login.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobdevproject.login.presentation.UiStates

@Composable
fun PhoneSignInScreen(
    modifier: Modifier = Modifier,
    onSendCodeToPhoneNumber: (phoneNumber: String) -> Unit,
    onBackNavigate: () -> Unit,
    uiState: UiStates,
) {
    var phoneNumberText by rememberSaveable { mutableStateOf("+") }
    var isError by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBarCompose(title = "Sign In with SMS") {
                onBackNavigate()
            }
        }
    ) { paddingValues: PaddingValues ->

        if (uiState is UiStates.Loading)
            androidx.compose.material.LinearProgressIndicator(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            )

        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = phoneNumberText,
                onValueChange = {
                    isError = false
                    phoneNumberText = if (phoneNumberText.isEmpty()) "+" else it
                },
                isError = isError,
                supportingText = {
                    if (isError)
                        Text(text = "Invalid Phone Number")
                    else
                        Text(text = "Write down a valid Phone Number")
                },
                label = { Text(text = "Phone Number") },
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    if (phoneNumberText.isNotEmpty())
                        IconButton(onClick = { phoneNumberText = "+" }) {
                            Icon(Icons.Default.Clear, "Clear text")
                        }
                },
                enabled = (uiState is UiStates.Show),
                maxLines = 1,
            )

            Spacer(modifier = modifier.height(24.dp))

            OutlinedButton(
                onClick = {
                    onSendCodeToPhoneNumber(phoneNumberText)
                    keyboardController?.hide()
                },
                enabled = (uiState is UiStates.Show)
            ) {
                Text(text = "Get a code")
            }

            Spacer(modifier = modifier.height(100.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhoneLogInPreview() {
    PhoneSignInScreen(onSendCodeToPhoneNumber = {}, onBackNavigate = {}, uiState = UiStates.Loading)
}