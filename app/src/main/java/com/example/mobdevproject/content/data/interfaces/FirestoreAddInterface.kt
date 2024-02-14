package com.example.mobdevproject.content.data.interfaces

import com.example.mobdevproject.content.data.FirestoreData

interface FirestoreAddInterface {

    fun <T : FirestoreData> addFirestoreDataOneCollection(
        data: T,
        collection: String,
        documentId: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

    fun <T : FirestoreData> addFirestoreDataTwoCollection(
        data: T,
        collection1: String,
        collection2: String,
        documentPath: String,
        documentId: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

}