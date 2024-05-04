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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.mobdevproject.additional.isEmail
import com.example.mobdevproject.login.presentation.UiStates

@Composable
fun EmailSignInScreen(
    modifier: Modifier = Modifier,
    onNavigationTopBar: () -> Unit,
    onSignInEmail: (email: String) -> Unit,
    uiState: UiStates,
) {
    var emailText by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBarCompose(title = "Email Sign In") {
                onNavigationTopBar()
            }
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = emailText,
                onValueChange = {
                    isError = false
                    emailText = it
                },
                isError = isError,
                supportingText = {
                    if (isError)
                        Text(text = "Invalid Email")
                    else
                        Text(text = "Write down a valid Email")
                },
                label = { Text(text = "Email") },
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                trailingIcon = {
                    if (emailText.isNotEmpty())
                        IconButton(onClick = { emailText = "" }) {
                            Icon(Icons.Default.Clear, "Clear text")
                        }
                },
                enabled = (uiState is UiStates.Show),
            )

            Spacer(modifier = modifier.height(24.dp))

            FilledTonalButton(
                onClick = {
                    keyboardController?.hide()
                    if (emailText.isEmail())
                        onSignInEmail(emailText)
                    else
                        isError = true
                },
                enabled = (uiState is UiStates.Show),
            ) {
                Text(text = "Sign In")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarCompose(
    title: String,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back Icon")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun EmailLogInPreview() {
    EmailSignInScreen(
        onNavigationTopBar = { /*TODO*/ },
        onSignInEmail = {},
        uiState = UiStates.Loading
    )
}