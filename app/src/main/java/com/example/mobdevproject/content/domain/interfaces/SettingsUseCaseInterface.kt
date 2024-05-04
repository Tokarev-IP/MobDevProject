package com.example.mobdevproject.content.domain.interfaces

import com.google.firebase.auth.FirebaseUser

interface SettingsUseCaseInterface {

     fun signOut()

    fun getUserEmail(user: FirebaseUser): String?

    fun isEmailVerified(user: FirebaseUser): Boolean

    fun updateEmail(
        user: FirebaseUser,
        email: String,
        isSuccessful: () -> Unit,
        isCanceled: () -> Unit,
    )

    fun verifyEmail(
        user: FirebaseUser,
        isSuccessful: () -> Unit,
        isCanceled: () -> Unit,
    )

}