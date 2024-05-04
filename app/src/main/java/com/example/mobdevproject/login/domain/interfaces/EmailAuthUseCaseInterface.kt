package com.example.mobdevproject.login.domain.interfaces

interface EmailAuthUseCaseInterface {

    fun sendEmailSignInLink(
        email: String,
        onSuccess:(msg: String) -> Unit,
        onCanceled:(e: String) -> Unit,
    )

}