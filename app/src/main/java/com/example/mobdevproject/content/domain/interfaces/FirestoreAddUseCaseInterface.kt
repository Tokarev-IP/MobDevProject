package com.example.mobdevproject.content.domain.interfaces

interface FirestoreAddUseCaseInterface {

    fun addMyMenuId(
        collection: String,
        userId: String,
        menuId: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String) -> Unit
    )
}