package com.example.mobdevproject.content.data.interfaces

import com.google.firebase.firestore.QuerySnapshot

interface FirestoreSearchInterface {

    fun getListMenuMainDataWithPaging(
        collection: String,
        onSuccess: (result: QuerySnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

    fun getListMenuMainDataByName(
        name: String,
        collection: String,
        onSuccess: (result: QuerySnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

}