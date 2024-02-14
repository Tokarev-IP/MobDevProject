package com.example.mobdevproject.content.data.repositories

import android.net.Uri
import com.example.mobdevproject.content.data.interfaces.StorageInterface
import com.google.firebase.Firebase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.storage
import javax.inject.Inject

class StorageRepository @Inject constructor() : StorageInterface {

    private val storageRef = Firebase.storage.reference

    override fun uploadFile(
        pathString: String,
        uri: Uri,
        onSuccess: (task: UploadTask.TaskSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    ) {
        storageRef.child(pathString).putFile(uri)
            .addOnSuccessListener { task: UploadTask.TaskSnapshot ->
                onSuccess(task)
            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }

    override fun downloadFile(
        pathString: String,
        uri: Uri,
        onSuccess: (task: FileDownloadTask.TaskSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    ) {
        storageRef.child(pathString).getFile(uri)
            .addOnSuccessListener { task: FileDownloadTask.TaskSnapshot ->
                onSuccess(task)
            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }
}