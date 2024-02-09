package com.example.mobdevproject.login.domain.interfaces

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions

interface AuthUseCaseInterface {

    fun getCurrentUser(
        onUser: (user: FirebaseUser) -> Unit,
        onNullUser: (msg: String) -> Unit,
    )

    fun signInWithCredential(
        authCredential: AuthCredential,
        onSuccessful: () -> Unit,
        onCanceled: (e: String) -> Unit,
    )

    fun getSignInCodeSMS(
        phoneAuthOptions: PhoneAuthOptions,
    )

    fun signInWithGoogle(
        completedTask: Task<GoogleSignInAccount>,
        onSuccessful: () -> Unit,
        onCanceled: (e: String) -> Unit,
    )

    fun getCredential(
        verificationId: String,
        code: String,
        onCredential: (credential: PhoneAuthCredential) -> Unit,
    )
}