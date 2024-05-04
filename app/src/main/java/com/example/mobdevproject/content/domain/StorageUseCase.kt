package com.example.mobdevproject.content.domain

import android.net.Uri
import com.example.mobdevproject.content.data.interfaces.StorageInterface
import com.example.mobdevproject.content.domain.interfaces.StorageUseCaseInterface
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StorageUseCase @Inject constructor(
    private val storageInterface: StorageInterface,
) : StorageUseCaseInterface {

    override suspend fun uploadMenuPicture(
        pathString: String,
        menuId: String,
        byteArray: ByteArray,
    ): Unit? {
        return suspendCoroutine { continuation: Continuation<Unit?> ->
            storageInterface.uploadFileByteArray(
                pathString = "$menuId/$pathString/main_picture",
                bytes = byteArray,
                onSuccess = {
                    continuation.resume(null)
                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }

    override suspend fun uploadMenuDishPicture(
        pathString: String,
        menuId: String,
        dishId: String,
        byteArray: ByteArray,
    ): Unit? {
        return suspendCoroutine { continuation: Continuation<Unit?> ->
            storageInterface.uploadFileByteArray(
                pathString = "$menuId/$pathString/$dishId",
                bytes = byteArray,
                onSuccess = {
                    continuation.resume(null)
                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }

    override suspend fun downloadMenuPictureUri(
        pathString: String,
        menuId: String,
    ): Uri? {
        return suspendCoroutine { continuation: Continuation<Uri?> ->
            storageInterface.downloadFileUri(
                pathString = "$menuId/$pathString/main_picture",
                onSuccess = { uri: Uri? ->
                    continuation.resume(uri)
                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }

    override suspend fun downloadMenuDishPictureUri(
        pathString: String,
        menuId: String,
        dishId: String
    ): Uri? {
        return suspendCoroutine { continuation: Continuation<Uri?> ->
            storageInterface.downloadFileUri(
                pathString = "$menuId/$pathString/$dishId",
                onSuccess = { uri: Uri? ->
                    continuation.resume(uri)
                },
                onFailure = { e: Exception ->
                    continuation.resumeWithException(e)
                }
            )
        }
    }

}