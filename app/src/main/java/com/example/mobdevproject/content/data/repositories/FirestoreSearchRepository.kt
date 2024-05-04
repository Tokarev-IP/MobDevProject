package com.example.mobdevproject.content.data.repositories

import com.example.mobdevproject.content.data.interfaces.FirestoreSearchInterface
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreSearchRepository @Inject constructor() : FirestoreSearchInterface {

    private val db: FirebaseFirestore = Firebase.firestore

    override fun getListMenuMainDataWithPaging(
        collection: String,
        onSuccess: (result: QuerySnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    ) {
        db.collection(collection)
            .get()
            .addOnSuccessListener { data: QuerySnapshot ->
                onSuccess(data)
            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }

    override fun getListMenuMainDataByName(
        name: String,
        collection: String,
        onSuccess: (result: QuerySnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        db.collection(collection)
            .whereEqualTo("name", name)
            .get()
            .addOnSuccessListener { it: QuerySnapshot ->
                onSuccess(it)
            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }
}