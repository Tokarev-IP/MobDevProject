package com.example.mobdevproject.content.domain

import com.example.mobdevproject.content.data.MyMenuId
import com.example.mobdevproject.content.data.interfaces.FirestoreReadInterface
import com.example.mobdevproject.content.domain.interfaces.FirestoreReadUseCaseInterface
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class FirestoreReadUseCase @Inject constructor(
    private val firestoreReadInterface: FirestoreReadInterface,
) : FirestoreReadUseCaseInterface {

    override fun checkMyMenu(
        collection: String,
        userId: String,
        onResult: (document: MyMenuId) -> Unit,
        onEmpty: () -> Unit,
        onFailure: (msg: String) -> Unit
    ) {
        firestoreReadInterface.readDocumentFromOneCollection(
            collection = collection,
            documentPath = userId,
            onSuccess = { result: DocumentSnapshot ->
                val document = result.toObject(MyMenuId::class.java)
                if (document != null)
                    onResult(document)
                else
                    onEmpty()
            },
            onFailure = {e: Exception ->
                onFailure(e.cause.toString())
            }
        )
    }
}