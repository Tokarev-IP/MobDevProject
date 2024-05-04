package com.example.mobdevproject.content.data.interfaces

import android.net.Uri
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.UploadTask

interface StorageInterface {

    fun uploadFile(
        pathString: String,
        fileUri: Uri,
        onSuccess: (task: UploadTask.TaskSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

    fun uploadFileByteArray(
        pathString: String,
        bytes: ByteArray,
        onSuccess: (task: UploadTask.TaskSnapshot) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )

    fun downloadFileUri(
        pathString: String,
        onSuccess: (uri: Uri?) -> Unit,
        onFailure: (e: Exception) -> Unit,
    )
}