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
        fileUri: Uri,
        onSuccess: (task: UploadTask.TaskSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    ) {
        storageRef.child(pathString).putFile(fileUri)
            .addOnSuccessListener { task: UploadTask.TaskSnapshot ->
                onSuccess(task)
            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }

    override fun uploadFileByteArray(
        pathString: String,
        bytes: ByteArray,
        onSuccess: (task: UploadTask.TaskSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    ) {
        storageRef.child(pathString).putBytes(bytes)
            .addOnSuccessListener { task: UploadTask.TaskSnapshot ->
                onSuccess(task)
            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }

    override fun downloadFileUri(
        pathString: String,
        onSuccess: (uri: Uri?) -> Unit,
        onFailure: (e: Exception) -> Unit,
    ) {
        storageRef.child(pathString).downloadUrl
            .addOnSuccessListener { uri: Uri? ->
                onSuccess(uri)
            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }
}