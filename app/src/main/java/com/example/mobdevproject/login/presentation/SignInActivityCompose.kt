package com.example.mobdevproject.login.presentation

import android.util.Log
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobdevproject.login.presentation.screens.EmailSignInScreen
import com.example.mobdevproject.login.presentation.screens.PhoneCodeSignInScreen
import com.example.mobdevproject.login.presentation.screens.PhoneSignInScreen
import com.example.mobdevproject.login.presentation.screens.SignInScreen
import kotlinx.coroutines.launch

@Composable
fun SignInActivityCompose(
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = hiltViewModel(),
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
            Log.d("DAVAI", "PhoneSignInScreen screenState")
            navController.navigate(NavigateRoutes.PhoneSignInScreen.route)
        }

        is ScreenStates.EmailSignInScreen -> {
            Log.d("DAVAI", "EmailSignInScreen screenState")
            navController.navigate(NavigateRoutes.EmailSignInScreen.route)
        }

        is ScreenStates.SignInScreen -> {
            Log.d("DAVAI", "SignInScreen screenState")
            navController.navigate(NavigateRoutes.SignInScreen.route)
        }

        is ScreenStates.PhoneCodeSignInScreen -> {
            Log.d("DAVAI", "PhoneCodeSignInScreen screenState")
            val phoneNumber = (screenState as ScreenStates.PhoneCodeSignInScreen).phoneNumber
            navController.navigate(NavigateRoutes.PhoneCodeSignInScreen.route + "/$phoneNumber")
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
                Log.d("DAVAI", "SignInScreen state")
                SignInScreen(
                    onEmailLogin = { signInViewModel.setScreenState(ScreenStates.EmailSignInScreen) },
                    onPhoneLogin = { signInViewModel.setScreenState(ScreenStates.PhoneSignInScreen) },
                    onGoogleLogin = { onGoogleSignIn() },
                    uiState = uiState,
                )
            }

            composable(NavigateRoutes.EmailSignInScreen.route) {
                Log.d("DAVAI", "EmailSignInScreen state")
                EmailSignInScreen(
                    onNavigationTopBar = { navController.popBackStack() },
                    onSignInEmail = { email: String ->
                        onEmailSignIn(email)
                    },
                    uiState = uiState,
                )
            }

            composable(NavigateRoutes.PhoneSignInScreen.route) {
                Log.d("DAVAI", "PhoneSignInScreen state")
                PhoneSignInScreen(
                    onSendCodeToPhoneNumber = { phoneNumber: String ->
                        onSendCodeToPhoneNumber(phoneNumber)
                    },
                    onBackNavigate = { navController.popBackStack() },
                    uiState = uiState,
                )
            }

            composable(
                NavigateRoutes.PhoneCodeSignInScreen.route + "/{phoneNumber}",
                arguments = listOf(
                    navArgument("phoneNumber") { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                Log.d("DAVAI", "PhoneCodeSignInScreen state")
                val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
                PhoneCodeSignInScreen(
                    phoneNumber = phoneNumber,
                    onChangePhoneNumber = {
                        navController.popBackStack()
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