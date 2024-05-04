package com.example.mobdevproject.content.domain

import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.data.MyMenuId
import com.example.mobdevproject.content.data.interfaces.FirestoreReadInterface
import com.example.mobdevproject.content.domain.interfaces.FirestoreReadUseCaseInterface
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreReadUseCase @Inject constructor(
    private val firestoreReadInterface: FirestoreReadInterface,
) : FirestoreReadUseCaseInterface {

    override suspend fun checkMyMenu(
        collection: String,
        userId: String
    ): MyMenuId? {
        return suspendCoroutine { continuation: Continuation<MyMenuId?> ->
            firestoreReadInterface.readDocumentFromOneCollection(
                collection = collection,
                documentPath = userId,
                onSuccess = { result: DocumentSnapshot ->
                    val document: MyMenuId? = result.toObject(MyMenuId::class.java)
                    continuation.resume(document)
                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }

    override suspend fun getMenuMainData(
        collection: String,
        menuId: String,
    ): MenuMainData? {
        return suspendCoroutine { continuation: Continuation<MenuMainData?> ->
            firestoreReadInterface.readDocumentFromOneCollection(
                collection = collection,
                documentPath = menuId,
                onSuccess = { result: DocumentSnapshot ->
                    val document: MenuMainData? = result.toObject(MenuMainData::class.java)
                    continuation.resume(document)
                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }

    override suspend fun getMenuDataList(
        collection1: String,
        collection2: String,
        menuId: String
    ): List<MenuData> {
        return suspendCoroutine { continuation: Continuation<List<MenuData>> ->
            firestoreReadInterface.readDataFromTwoCollection(
                collection1 = collection1,
                collection2 = collection2,
                documentPath = menuId,
                onSuccess = { result: QuerySnapshot ->
                    val menuDataList = mutableListOf<MenuData>()
                    for (document in result) {
                        if (document != null) {
                            val data = document.toObject(MenuData::class.java)
                            menuDataList.add(data)
                        }
                    }
                    continuation.resume(menuDataList)
                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }
}