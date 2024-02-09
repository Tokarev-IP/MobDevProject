package com.example.mobdevproject.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobdevproject.login.domain.interfaces.AuthUseCaseInterface
import com.example.mobdevproject.login.domain.interfaces.EmailAuthUseCaseInterface
import com.example.mobdevproject.login.domain.interfaces.PhoneAuthProcessingInterface
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authUseCaseInterface: AuthUseCaseInterface,
    private val emailAuthUseCaseInterface: EmailAuthUseCaseInterface,
    private val phoneAuthProcessingInterface: PhoneAuthProcessingInterface,
) : ViewModel() {

    private var verificationId: String = ""
    private var phoneNumber: String = ""

    init {
        viewModelScope.launch {
            phoneAuthProcessingInterface.getPhoneAuthFlow().collect { state: PhoneAuthStates? ->
                when (state) {
                    is PhoneAuthStates.CodeWasSent -> {
                        verificationId = state.verificationId
                        uiState.value = UiStates.Show
                        screenState.value = ScreenStates.PhoneCodeSignInScreen(phoneNumber)
                    }

                    is PhoneAuthStates.VerificationFailed -> {
                        snackbarState.value = state.msg
                        uiState.value = UiStates.Show
                    }

                    is PhoneAuthStates.VerificationCompleted -> {
                        signInWithCredential(state.credential)
                    }
                }
            }
        }
    }

    private val screenState: MutableStateFlow<ScreenStates?> = MutableStateFlow(null)
    private val screenStateFlow = screenState.asStateFlow()

    private val uiState: MutableStateFlow<UiStates> = MutableStateFlow(UiStates.Show)
    private val uiStateFlow = uiState.asStateFlow()

    private val userState: MutableStateFlow<FirebaseUser?> = MutableStateFlow(null)
    private val userStateFlow = userState.asStateFlow()

    private val snackbarState: MutableStateFlow<String?> = MutableStateFlow(null)
    private val snackbarStateFlow = snackbarState.asStateFlow()

    fun getScreenStateFlow() = screenStateFlow
    fun getUiStateFlow() = uiStateFlow
    fun getUserStateFlow() = userStateFlow
    fun getSnackbarStateFlow() = snackbarStateFlow

    fun setIntent(uiIntent: UiIntents) {
        when (uiIntent) {
            is UiIntents.SignInWithEmail -> {
                uiState.value = UiStates.Loading
                sendEmailSignInLink(uiIntent.email)
            }

            is UiIntents.SignInWithGoogle -> {
                uiState.value = UiStates.Loading
                authUseCaseInterface.signInWithGoogle(
                    completedTask = uiIntent.task,
                    onSuccessful = {
                        getUser()
                    },
                    onCanceled = { msg: String ->
                        snackbarState.value = msg
                    }
                )
            }

            is UiIntents.SignInWithPhone -> {
                uiState.value = UiStates.Loading
                authUseCaseInterface.getSignInCodeSMS(uiIntent.phoneAuthOptions)
                phoneNumber = uiIntent.phoneNumber
            }

            is UiIntents.CheckUserID -> {
                uiState.value = UiStates.Loading
                getUser()
            }

            is UiIntents.SignWithSmsCode -> {
                uiState.value = UiStates.Loading
                authUseCaseInterface.getCredential(
                    verificationId = verificationId,
                    code = uiIntent.code,
                    onCredential = { credential: PhoneAuthCredential ->
                        signInWithCredential(credential)
                    }
                )
            }
        }
    }

    fun setScreenState(state: ScreenStates) {
        screenState.value = state
    }

    fun setUiState(state: UiStates) {
        uiState.value = state
    }

    private fun sendEmailSignInLink(email: String) {
        emailAuthUseCaseInterface.sendEmailSignInLink(
            email = email,
            onSuccess = { msg: String ->
                snackbarState.value = msg
                uiState.value = UiStates.Show
            },
            onCanceled = { e: String ->
                snackbarState.value = e
                uiState.value = UiStates.Show
            },
        )
    }

    private fun getUser() {
        authUseCaseInterface.getCurrentUser(
            onUser = { firebaseUser: FirebaseUser ->
                userState.value = firebaseUser
                uiState.value = UiStates.Show
            },
            onNullUser = {
                userState.value = null
                uiState.value = UiStates.Show
            },
        )
    }

    private fun signInWithCredential(
        credential: PhoneAuthCredential,
    ) {
        authUseCaseInterface.signInWithCredential(
            authCredential = credential,
            onSuccessful = {
                getUser()
            },
            onCanceled = { msg: String ->
                snackbarState.value = msg
                uiState.value = UiStates.Show
            },
        )
    }
}