package com.example.mobdevproject.content.data.interfaces

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface FirestoreReadInterface {

    fun readDataFromOneCollection(
        collection1: String,
        onSuccess: (result: QuerySnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

    fun readDataFromTwoCollection(
        collection1: String,
        collection2: String,
        documentPath: String,
        onSuccess: (result: QuerySnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

    fun readDocumentFromOneCollection(
        collection: String,
        documentPath: String,
        onSuccess: (result: DocumentSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

    fun readDocumentFromTwoCollection(
        collection1: String,
        collection2: String,
        documentPath1: String,
        documentPath2: String,
        onSuccess: (result: DocumentSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

}