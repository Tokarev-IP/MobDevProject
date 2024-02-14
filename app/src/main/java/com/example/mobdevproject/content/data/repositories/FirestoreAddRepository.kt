package com.example.mobdevproject.content.data.repositories

import com.example.mobdevproject.content.data.FirestoreData
import com.example.mobdevproject.content.data.interfaces.FirestoreAddInterface
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import javax.inject.Inject

class FirestoreAddRepository @Inject constructor() : FirestoreAddInterface {

    private val db: FirebaseFirestore = Firebase.firestore

    override fun <T : FirestoreData> addFirestoreDataOneCollection(
        data: T,
        collection: String,
        documentId: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit,
    ) {
        Firebase.firestore.collection(collection).document(documentId)
            .set(data)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }

    override fun <T : FirestoreData> addFirestoreDataTwoCollection(
        data: T,
        collection1: String,
        collection2: String,
        documentPath: String,
        documentId: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit,
    ) {
        db.collection(collection1).document(documentPath)
            .collection(collection2).document(documentId)
            .set(data)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }
}