package com.example.mobdevproject.content.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobdevproject.content.presentation.SettingsViewModel
import com.example.mobdevproject.content.presentation.UiStates

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onSignOut:() -> Unit,
) {

    val state: UiStates by settingsViewModel.getUiStatesFlow().collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        when (state) {
            is UiStates.Loading -> {
                CircularProgressIndicator()
            }

            is UiStates.Show -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    OutlinedButton(
                        modifier = modifier.padding(24.dp),
                        onClick = {
                            onSignOut()
                        }
                    ) {
                        Text(text = "Sign out")
                    }
                }
            }
        }
    }
}