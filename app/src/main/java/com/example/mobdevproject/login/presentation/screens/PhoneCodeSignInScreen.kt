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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneCodeSignInScreen(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    onChangePhoneNumber: () -> Unit,
    onSignInWithSmsCode: (code: String) -> Unit,
    uiState: UiStates,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "SMS code") })
        },
    ) { paddingValues: PaddingValues ->

        if (uiState is UiStates.Loading)
            androidx.compose.material.LinearProgressIndicator(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            var codeText by rememberSaveable { mutableStateOf("") }
            var isError by rememberSaveable { mutableStateOf(false) }
            val keyboardController = LocalSoftwareKeyboardController.current

            OutlinedTextField(
                value = codeText,
                onValueChange = {
                    isError = false
                    codeText = it
                },
                isError = isError,
                supportingText = {
                    if (isError)
                        Text(text = "Invalid Code")
                    else
                        Text(text = "Write down the code from SMS")
                },
                label = { Text(text = "Code") },
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    if (codeText.isNotEmpty())
                        IconButton(onClick = { codeText = "" }) {
                            Icon(Icons.Default.Clear, "Clear text")
                        }
                },
                enabled = (uiState is UiStates.Show)
            )

            Spacer(modifier = modifier.height(24.dp))

            OutlinedButton(
                onClick = {
                    onSignInWithSmsCode(codeText)
                    keyboardController?.hide()
                },
                enabled = codeText.length == 6 && (uiState is UiStates.Show),
            ) {
                Text(text = "Sign In")
            }

            Spacer(modifier = modifier.height(60.dp))

            Text(text = "The code was sent to number $phoneNumber")

            TextButton(
                onClick = { onChangePhoneNumber() },
                enabled = (uiState is UiStates.Show)
            ) {
                Text(text = "Change the phone number")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhoneCodeSignInPreview() {
    PhoneCodeSignInScreen(
        phoneNumber = "1234567",
        onChangePhoneNumber = { /*TODO*/ },
        onSignInWithSmsCode = {},
        uiState = UiStates.Loading
    )
}