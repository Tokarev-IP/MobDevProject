package com.example.mobdevproject.content.domain

import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.data.MyMenuId
import com.example.mobdevproject.content.data.interfaces.FirestoreAddInterface
import com.example.mobdevproject.content.domain.interfaces.FirestoreAddUseCaseInterface
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    override suspend fun addMenuMainData(
        collection: String,
        data: MenuMainData,
        menuId: String,
    ): Unit? {
        return suspendCoroutine { continuation: Continuation<Unit?> ->
            firestoreAddInterface.addFirestoreDataOneCollection(
                data = data,
                collection = collection,
                documentId = menuId,
                onSuccess = {
                    continuation.resume(null)
                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }

    override suspend fun addMenuData(
        collection1: String,
        collection2: String,
        data: MenuData,
        menuId: String,
        documentId: String,
    ): Unit? {
        return suspendCoroutine { continuation: Continuation<Unit?> ->
            firestoreAddInterface.addFirestoreDataTwoCollection(
                data = data,
                collection1 = collection1,
                collection2 = collection2,
                documentPath = menuId,
                documentId = documentId,
                onSuccess = {
                    continuation.resume(null)
                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }
}