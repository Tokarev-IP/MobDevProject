package com.example.mobdevproject.login.presentation

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions

sealed class UiIntents {
    data class SignInWithGoogle(val task: Task<GoogleSignInAccount>) : UiIntents()
    data class SignInWithEmail(val email: String) : UiIntents()
    data class SignInWithPhone(val phoneAuthOptions: PhoneAuthOptions, val phoneNumber: String) :
        UiIntents()

    data class SignWithSmsCode(val code: String) : UiIntents()
    data object CheckUserID : UiIntents()
}

interface ScreenStates {
    object EmailSignInScreen : ScreenStates
    object PhoneSignInScreen : ScreenStates
    object SignInScreen : ScreenStates
    data class PhoneCodeSignInScreen(val phoneNumber: String) : ScreenStates
}

sealed interface UiStates {
    data object Show : UiStates
    data object Error : UiStates
    data object Loading : UiStates
    data object CodeWasSent : UiStates
    data object UserWasFound : UiStates
}

interface PhoneAuthStates {
    data class VerificationCompleted(val credential: PhoneAuthCredential) : PhoneAuthStates
    data class VerificationFailed(val msg: String) : PhoneAuthStates
    data class CodeWasSent(val verificationId: String) : PhoneAuthStates
}

sealed class NavigateRoutes(val route: String) {
    data object EmailSignInScreen : NavigateRoutes(route = "EmailSignInScreen")
    data object PhoneCodeSignInScreen : NavigateRoutes(route = "PhoneCodeSignInScreen")
    data object PhoneSignInScreen : NavigateRoutes(route = "PhoneSignInScreen")
    data object SignInScreen : NavigateRoutes(route = "SignInScreen")
}