package com.example.mobdevproject.content.presentation

import androidx.lifecycle.ViewModel
import com.example.mobdevproject.content.domain.interfaces.SettingsUseCaseInterface
import com.example.mobdevproject.login.domain.interfaces.AuthUseCaseInterface
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCaseInterface: SettingsUseCaseInterface,
    private val authUseCaseInterface: AuthUseCaseInterface,
) : ViewModel() {

    private var user: FirebaseUser? = null

    private val emailState: MutableStateFlow<String> = MutableStateFlow("")
    private val emailStateFlow = emailState.asStateFlow()

    private val isEmailVerifiedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isEmailVerifiedStateFlow = isEmailVerifiedState.asStateFlow()

    private val uiStates: MutableStateFlow<UiStates> = MutableStateFlow(UiStates.Show)
    private val uiStatesFlow = uiStates.asStateFlow()

    fun getEmailStateFlow() = emailStateFlow
    fun getIsEmailVerifiedStateFlow() = isEmailVerifiedStateFlow
    fun getUiStatesFlow() = uiStatesFlow

    fun setUiIntent(uiIntents: SettingsUiIntents) {
        when (uiIntents) {
            is SettingsUiIntents.LoadData -> {
                uiStates.value = UiStates.Loading
                authUseCaseInterface.getCurrentUser(
                    onUser = { data: FirebaseUser ->
                        user = data
                        emailIsEmailVerified(data)
                        getUserEmail(data)
                        uiStates.value = UiStates.Show
                    },
                    onNullUser = {}
                )

            }

            is SettingsUiIntents.SignOut -> {}

            is SettingsUiIntents.UpdateEmail -> {
                uiStates.value = UiStates.Loading
                user?.let { firebaseUser: FirebaseUser ->
                    updateUserEmail(firebaseUser, uiIntents.email)
                }
                uiStates.value = UiStates.Show
            }

            is SettingsUiIntents.VerifyEmail -> {
                uiStates.value = UiStates.Loading
                user?.let { firebaseUser: FirebaseUser ->
                    verifyUserEmail(firebaseUser)
                }
                uiStates.value = UiStates.Show
            }
        }
    }

    private fun getUserEmail(user: FirebaseUser) {
        val email: String? = settingsUseCaseInterface.getUserEmail(user)
        if (email != null)
            emailState.value = email
    }

    private fun emailIsEmailVerified(user: FirebaseUser) {
        val isEmailVerified = settingsUseCaseInterface.isEmailVerified(user)
        isEmailVerifiedState.value = isEmailVerified
    }

    private fun updateUserEmail(
        user: FirebaseUser,
        email: String,
    ) {
        settingsUseCaseInterface.updateEmail(
            user = user,
            email = email,
            isSuccessful = {
                emailState.value = email
            },
            isCanceled = {}
        )
    }

    private fun verifyUserEmail(
        user: FirebaseUser
    ) {
        settingsUseCaseInterface.verifyEmail(
            user = user,
            isSuccessful = {
                isEmailVerifiedState.value = true
            },
            isCanceled = {}
        )
    }

    fun signOut(
        onSuccess:() -> Unit,
        userExists:() -> Unit,
    ) {
        settingsUseCaseInterface.signOut()

        authUseCaseInterface.getCurrentUser(
            onUser = { userExists() },
            onNullUser = { onSuccess() }
        )
    }
}