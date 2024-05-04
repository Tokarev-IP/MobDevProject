package com.example.mobdevproject.login.domain

import com.example.mobdevproject.login.domain.interfaces.PhoneAuthProcessingInterface
import com.example.mobdevproject.login.presentation.PhoneAuthStates
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhoneAuthProcessing @Inject constructor(): PhoneAuthProcessingInterface {

    private val phoneAuthState: MutableStateFlow<PhoneAuthStates?> = MutableStateFlow(null)
    private val phoneAuthFlow = phoneAuthState.asStateFlow()
    override fun getPhoneAuthFlow() = phoneAuthFlow
    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        phoneAuthState.value = PhoneAuthStates.VerificationCompleted(credential)
    }

    override fun onVerificationFailed(e: FirebaseException) {
        if (e is FirebaseAuthInvalidCredentialsException) {
            // Invalid request
        } else if (e is FirebaseTooManyRequestsException) {
            // The SMS quota for the project has been exceeded
        } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
            // reCAPTCHA verification attempted with null Activity
        }
        phoneAuthState.value = PhoneAuthStates.VerificationFailed(e.message.toString())
    }

    override fun onCodeSent(verificationId: String) {
        phoneAuthState.value = PhoneAuthStates.CodeWasSent(verificationId)
    }
}