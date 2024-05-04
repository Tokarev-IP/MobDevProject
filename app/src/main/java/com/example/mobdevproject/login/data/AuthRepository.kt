package com.example.mobdevproject.login.data

import com.example.mobdevproject.login.data.interfaces.AuthRepositoryInterface
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() : AuthRepositoryInterface {

    private val auth = FirebaseAuth.getInstance()

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun signOut(): Unit = auth.signOut()

    override fun getUserEmail(user: FirebaseUser): String? = user.email

    override fun isEmailVerified(user: FirebaseUser): Boolean = user.isEmailVerified

    override fun sendEmailVerification(
        user: FirebaseUser,
        onResult: (task: Task<Void>) -> Unit,
        ) {
        user.sendEmailVerification().addOnCompleteListener { task: Task<Void> ->
            onResult(task)
        }
    }

    override fun updateEmail(
        user: FirebaseUser,
        email: String,
        onResult: (task: Task<Void>) -> Unit,
    ) {
        user.verifyBeforeUpdateEmail(email).addOnCompleteListener { task: Task<Void> ->
            onResult(task)
        }
    }

    override fun signInWithCridential(
        authCredential: AuthCredential,
        onResult: (task: Task<AuthResult>) -> Unit,
    ) {
        auth.signInWithCredential(authCredential).addOnCompleteListener { task: Task<AuthResult> ->
            onResult(task)
        }
    }

    override fun signInWithEmailLink(
        email: String,
        emailLink: String,
        onResult: (task: Task<AuthResult>) -> Unit,
    ) {
        auth.signInWithEmailLink(email, emailLink).addOnCompleteListener { task: Task<AuthResult> ->
            onResult(task)
        }
    }

    override fun sendSignInEmailLink(
        email: String,
        actionCodeSettings: ActionCodeSettings,
        onResult: (task: Task<AuthResult>) -> Unit,
    ) {
        auth.sendSignInLinkToEmail(email, actionCodeSettings)
    }

    override fun sendSignInCodeToPhoneNumber(phoneAuthOptions: PhoneAuthOptions) {
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }

    override fun getCredential(
        verificationId: String,
        code: String,
    ) = PhoneAuthProvider.getCredential(verificationId, code)


}