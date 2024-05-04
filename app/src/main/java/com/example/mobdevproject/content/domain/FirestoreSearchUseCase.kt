package com.example.mobdevproject.content.domain

import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.data.interfaces.FirestoreSearchInterface
import com.example.mobdevproject.content.domain.interfaces.FirestoreSearchUseCaseInterface
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreSearchUseCase @Inject constructor(
    private val firestoreSearchInterface: FirestoreSearchInterface,
) : FirestoreSearchUseCaseInterface {
    override suspend fun getAllMenu(
        collection: String,
    ): List<MenuMainData> {
        return suspendCoroutine { continuation: Continuation<List<MenuMainData>> ->
            firestoreSearchInterface.getListMenuMainDataWithPaging(
                collection = collection,
                onSuccess = { result: QuerySnapshot ->
                    val menuDataList = mutableListOf<MenuMainData>()
                    for (document in result) {
                        if (document != null) {
                            val data = document.toObject(MenuMainData::class.java)
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

    override suspend fun getMenuByName(
        name: String,
        collection: String
    ): List<MenuMainData> {
        return suspendCoroutine { continuation: Continuation<List<MenuMainData>> ->
            firestoreSearchInterface.getListMenuMainDataByName(
                name = name,
                collection = collection,
                onSuccess = {result: QuerySnapshot ->
                    val menuDataList = mutableListOf<MenuMainData>()
                    for (document in result) {
                        val data = document.toObject(MenuMainData::class.java)
                        if (document != null)
                            menuDataList.add(data)
                    }

                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }
}