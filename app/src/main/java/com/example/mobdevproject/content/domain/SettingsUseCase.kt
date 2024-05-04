package com.example.mobdevproject.content.domain

import com.example.mobdevproject.content.domain.interfaces.SettingsUseCaseInterface
import com.example.mobdevproject.login.data.interfaces.AuthRepositoryInterface
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsUseCase @Inject constructor(
    private val authRepositoryInterface: AuthRepositoryInterface,
) : SettingsUseCaseInterface {

    override fun signOut() {
        authRepositoryInterface.signOut()
    }

    override fun getUserEmail(user: FirebaseUser): String? {
        return authRepositoryInterface.getUserEmail(user)
    }

    override fun isEmailVerified(user: FirebaseUser): Boolean {
        return authRepositoryInterface.isEmailVerified(user)
    }

    override fun updateEmail(
        user: FirebaseUser,
        email: String,
        isSuccessful: () -> Unit,
        isCanceled: () -> Unit,
    ) {
        authRepositoryInterface.updateEmail(
            user,
            email,
            onResult = { it: Task<Void> ->
                if (it.isSuccessful)
                    isSuccessful()
                if (it.isCanceled)
                    isCanceled()
            }
        )
    }

    override fun verifyEmail(
        user: FirebaseUser,
        isSuccessful: () -> Unit,
        isCanceled: () -> Unit,
    ) {
        authRepositoryInterface.sendEmailVerification(
            user,
            onResult = { it: Task<Void> ->
                if (it.isSuccessful)
                    isSuccessful()
                if (it.isCanceled)
                    isCanceled()
            }
        )
    }
}