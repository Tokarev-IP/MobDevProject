package com.example.mobdevproject.login.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobdevproject.login.presentation.UiStates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onEmailLogin: () -> Unit,
    onPhoneLogin: () -> Unit,
    onGoogleLogin: () -> Unit,
    uiState: UiStates,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "Sign In") })
        },
    ) { paddingValues: PaddingValues ->

        if (uiState is UiStates.Loading)
            LinearProgressIndicator(
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

//            Button(
//                onClick = { onEmailLogin() },
//                enabled = (uiState is UiStates.Show),
//            ) {
//                Text(text = "Email")
//            }

            Spacer(modifier = modifier.height(16.dp))

            Button(
                onClick = { onPhoneLogin() },
                enabled = (uiState is UiStates.Show),
            ) {
                Text(text = "Phone number")
            }
            Spacer(modifier = modifier.height(16.dp))

//            Button(
//                onClick = { onGoogleLogin() },
//                enabled = (uiState is UiStates.Show),
//            ) {
//                Text(text = "Google Account")
//                Icon(Icons.Default.AccountCircle, "Account icon")
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogInScreenPreview() {
    SignInScreen(
        onEmailLogin = { /*TODO*/ },
        onPhoneLogin = { /*TODO*/ },
        onGoogleLogin = { /*TODO*/ },
        uiState = UiStates.Loading
    )
}