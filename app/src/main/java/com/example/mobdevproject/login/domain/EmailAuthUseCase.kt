package com.example.mobdevproject.login.domain

import com.example.mobdevproject.login.data.interfaces.AuthRepositoryInterface
import com.example.mobdevproject.login.domain.interfaces.EmailAuthUseCaseInterface
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.actionCodeSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailAuthUseCase @Inject constructor(
    private val authRepositoryInterface: AuthRepositoryInterface
) : EmailAuthUseCaseInterface{

    override fun sendEmailSignInLink(
        email: String,
        onSuccess: (msg: String) -> Unit,
        onCanceled: (e: String) -> Unit
    ) {
        authRepositoryInterface.sendSignInEmailLink(
            email = email,
            actionCodeSettings = getActionCodeSettings(),
            onResult = {task: Task<AuthResult> ->
                if (task.isSuccessful){
                    onSuccess("The link was sent to email")
                }
                task.exception?.let{e: Exception ->
                    when (e) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            onCanceled(e.message.toString())
                        }

                        is FirebaseNetworkException -> {
                            onCanceled(e.message.toString())
                        }

                        is FirebaseAuthException -> {
                            onCanceled(e.message.toString())
                        }

                        is FirebaseTooManyRequestsException -> {
                            onCanceled(e.message.toString())
                        }
                    }
                }
            }
        )
    }

    private fun getActionCodeSettings(): ActionCodeSettings
    = actionCodeSettings {
        // URL you want to redirect back to. The domain (www.example.com) for this
        // URL must be whitelisted in the Firebase Console.
        url = "https://mobdevproject-218c5.firebaseapp.com/__/auth/action?mode=action&oobCode=code"
        // This must be true
        handleCodeInApp = true
        iosBundleId = "com.example.ios"
        setAndroidPackageName(
            "com.example.android",
            false, // installIfNotAvailable
            "9", // minimumVersion
        )
    }
}