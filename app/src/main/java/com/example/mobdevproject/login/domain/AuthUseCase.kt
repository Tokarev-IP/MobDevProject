package com.example.mobdevproject.login.domain

import com.example.mobdevproject.login.data.interfaces.AuthRepositoryInterface
import com.example.mobdevproject.login.domain.interfaces.AuthUseCaseInterface
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthUseCase @Inject constructor(
    private val authRepositoryInterface: AuthRepositoryInterface,
) : AuthUseCaseInterface {

    override fun getCurrentUser(
        onUser: (user: FirebaseUser) -> Unit,
        onNullUser: (msg: String) -> Unit,
    ) {
        val user = authRepositoryInterface.getCurrentUser()
        user?.let { data: FirebaseUser ->
            onUser(data)
        }
        if (user == null)
            onNullUser("No user was gotten")
    }

    override fun signInWithCredential(
        authCredential: AuthCredential,
        onSuccessful: () -> Unit,
        onCanceled: (e: String) -> Unit,
    ) {
        authRepositoryInterface.signInWithCridential(
            authCredential = authCredential,
            onResult = { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    onSuccessful()
                }
                task.exception?.let { e: Exception ->
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

    override fun getSignInCodeSMS(phoneAuthOptions: PhoneAuthOptions) {
        authRepositoryInterface.sendSignInCodeToPhoneNumber(phoneAuthOptions)
    }

    override fun signInWithGoogle(
        completedTask: Task<GoogleSignInAccount>,
        onSuccessful: () -> Unit,
        onCanceled: (e: String) -> Unit,
    ) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

            signInWithCredential(
                authCredential = credential,
                onSuccessful = { onSuccessful() },
                onCanceled = { msg: String ->
                    onCanceled(msg)
                }
            )
        } catch (e: ApiException) {
            onCanceled(e.message.toString())
        }
    }

    override fun getCredential(
        verificationId: String,
        code: String,
        onCredential: (credential: PhoneAuthCredential) -> Unit,
    ) {
        val credential = authRepositoryInterface.getCredential(verificationId, code)
        onCredential(credential)
    }
}