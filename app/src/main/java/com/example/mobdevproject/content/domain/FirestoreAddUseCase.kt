package com.example.mobdevproject.content.domain

import com.example.mobdevproject.content.data.FirestoreData
import com.example.mobdevproject.content.data.MyMenuId
import com.example.mobdevproject.content.data.interfaces.FirestoreAddInterface
import com.example.mobdevproject.content.data.interfaces.FirestoreReadInterface
import com.example.mobdevproject.content.domain.interfaces.FirestoreAddUseCaseInterface
import javax.inject.Inject

class FirestoreAddUseCase @Inject constructor(
    private val firestoreAddInterface: FirestoreAddInterface,
) : FirestoreAddUseCaseInterface {

    override fun addMyMenuId(
        collection: String,
        userId: String,
        menuId: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String) -> Unit
    ) {
        firestoreAddInterface.addFirestoreDataOneCollection(
            data = MyMenuId(id = menuId),
            collection = collection,
            documentId = userId,
            onSuccess = {
                onSuccess()
            },
            onFailure = { e: Exception ->
                onFailure(e.cause.toString())
            }
        )
    }

}