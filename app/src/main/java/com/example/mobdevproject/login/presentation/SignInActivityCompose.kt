package com.example.mobdevproject.login.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobdevproject.login.presentation.screens.EmailSignInScreen
import com.example.mobdevproject.login.presentation.screens.PhoneCodeSignInScreen
import com.example.mobdevproject.login.presentation.screens.PhoneSignInScreen
import com.example.mobdevproject.login.presentation.screens.SignInScreen
import kotlinx.coroutines.launch

@Composable
fun SignInActivityCompose(
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = hiltViewModel<SignInViewModel>(),
    onGoogleSignIn: () -> Unit,
    onEmailSignIn: (email: String) -> Unit,
    onPhoneSignIn: (smsCode: String) -> Unit,
    onSendCodeToPhoneNumber: (phoneNUmber: String) -> Unit,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigateRoutes.SignInScreen.route,
) {
    val screenState by signInViewModel.getScreenStateFlow().collectAsState()
    val uiState by signInViewModel.getUiStateFlow().collectAsState()
    val snackbarState by signInViewModel.getSnackbarStateFlow().collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    when (screenState) {
        is ScreenStates.PhoneSignInScreen -> {
            navController.navigate(NavigateRoutes.PhoneSignInScreen.route)
        }

        is ScreenStates.EmailSignInScreen -> {
            navController.navigate(NavigateRoutes.EmailSignInScreen.route)
        }

        is ScreenStates.SignInScreen -> {
            navController.navigate(NavigateRoutes.SignInScreen.route)
        }

        is ScreenStates.PhoneCodeSignInScreen -> {
            navController.navigate(NavigateRoutes.PhoneCodeSignInScreen.route)
        }
    }

    snackbarState?.let { msg: String ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = msg,
                actionLabel = "Result of action",
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData: SnackbarData ->
                Snackbar(modifier = modifier.padding(horizontal = 24.dp)) {
                    Text(text = snackbarData.visuals.message)
                }
            }
        }
    ) { paddingValues: PaddingValues ->

        NavHost(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavigateRoutes.SignInScreen.route) {
                SignInScreen(
                    onEmailLogin = { signInViewModel.setScreenState(ScreenStates.EmailSignInScreen) },
                    onPhoneLogin = { signInViewModel.setScreenState(ScreenStates.PhoneSignInScreen) },
                    onGoogleLogin = { onGoogleSignIn() },
                    uiState = uiState,
                )
            }

            composable(NavigateRoutes.EmailSignInScreen.route) {
                EmailSignInScreen(
                    onNavigationTopBar = { navController.popBackStack() },
                    onSignInEmail = { email: String ->
                        onEmailSignIn(email)
                    },
                    uiState = uiState,
                )
            }

            composable(NavigateRoutes.PhoneSignInScreen.route) {
                PhoneSignInScreen(
                    onSendCodeToPhoneNumber = { phoneNumber: String ->
                        onSendCodeToPhoneNumber(phoneNumber)
                    },
                    onBackNavigate = { navController.popBackStack() },
                    uiState = uiState,
                )
            }

            composable(NavigateRoutes.PhoneCodeSignInScreen.route) {
                PhoneCodeSignInScreen(
                    phoneNumber = (screenState as ScreenStates.PhoneCodeSignInScreen).phoneNumber,
                    onChangePhoneNumber = {
                        // TODO:
                    },
                    onSignInWithSmsCode = { smsCode: String ->
                        onPhoneSignIn(smsCode)
                    },
                    uiState = uiState,
                )
            }
        }
    }
}