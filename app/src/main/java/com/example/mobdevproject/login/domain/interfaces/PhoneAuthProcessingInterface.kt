package com.example.mobdevproject.login.domain.interfaces

import com.example.mobdevproject.login.presentation.PhoneAuthStates
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.StateFlow

interface PhoneAuthProcessingInterface {

    fun getPhoneAuthFlow(): StateFlow<PhoneAuthStates?>
    fun onVerificationCompleted(credential: PhoneAuthCredential)
    fun onVerificationFailed(e: FirebaseException)
    fun onCodeSent(verificationId: String)
}