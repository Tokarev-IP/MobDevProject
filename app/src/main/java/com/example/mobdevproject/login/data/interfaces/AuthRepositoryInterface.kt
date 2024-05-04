package com.example.mobdevproject.login.data.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions

interface AuthRepositoryInterface {
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
    fun getUserEmail(user: FirebaseUser): String?
    fun isEmailVerified(user: FirebaseUser): Boolean
    fun sendEmailVerification(
        user: FirebaseUser,
        onResult: (task: Task<Void>) -> Unit,
    )
    fun updateEmail(
        user: FirebaseUser,
        email: String,
        onResult: (task: Task<Void>) -> Unit,
    )
    fun signInWithCridential(
        authCredential: AuthCredential,
        onResult: (task: Task<AuthResult>) -> Unit,
    )
    fun signInWithEmailLink(
        email: String,
        emailLink: String,
        onResult: (task: Task<AuthResult>) -> Unit,
    )
    fun sendSignInEmailLink(
        email: String,
        actionCodeSettings: ActionCodeSettings,
        onResult: (task: Task<AuthResult>) -> Unit,
    )
    fun sendSignInCodeToPhoneNumber(phoneAuthOptions: PhoneAuthOptions)
    fun getCredential(verificationId: String, code: String): PhoneAuthCredential
}