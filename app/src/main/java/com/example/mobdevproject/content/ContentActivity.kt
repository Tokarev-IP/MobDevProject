package com.example.mobdevproject.content

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobdevproject.content.presentation.ContentActivityCompose
import com.example.mobdevproject.content.presentation.SettingsUiIntents
import com.example.mobdevproject.content.presentation.SettingsViewModel
import com.example.mobdevproject.login.SignInActivity
import com.example.mobdevproject.login.presentation.UiIntents
import com.example.mobdevproject.ui.theme.MobDevProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MobDevProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StartUi()
                }
            }
        }
    }

    @Composable
    private fun StartUi(
    ) {
        val settingsViewModel: SettingsViewModel = hiltViewModel()

        ContentActivityCompose(
            context = this,
            onSignOut = {
                settingsViewModel.signOut(
                    onSuccess = { goToSignInActivity() },
                    userExists = {}
                )
            }
        )
    }

    private fun goToSignInActivity() {
        startActivity(Intent(this, SignInActivity::class.java))
    }
}