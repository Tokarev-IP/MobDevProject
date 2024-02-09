package com.example.mobdevproject.login.addition

import android.app.Activity
import com.example.mobdevproject.login.data.PhoneAuthCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import java.util.concurrent.TimeUnit

class PhoneAuthOptionsAddition(
    private val activity: Activity,
    private val phoneAuthCallback: PhoneAuthCallback
) {

    private val auth = FirebaseAuth.getInstance()

    fun getPhoneAuthOptions(phoneNumber: String) = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber(phoneNumber) // Phone number to verify
        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(activity) // Activity (for callback binding)
        .setCallbacks(phoneAuthCallback) // OnVerificationStateChangedCallbacks
        .build()
}