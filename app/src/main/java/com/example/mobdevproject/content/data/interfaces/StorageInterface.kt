package com.example.mobdevproject.content.data.interfaces

import android.net.Uri
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.UploadTask

interface StorageInterface {

    fun uploadFile(
        pathString: String,
        uri: Uri,
        onSuccess: (task: UploadTask.TaskSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

    fun downloadFile(
        pathString: String,
        uri: Uri,
        onSuccess: (task: FileDownloadTask.TaskSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )
}