package com.example.mobdevproject.content.domain.interfaces

import android.net.Uri

interface StorageUseCaseInterface {
    suspend fun uploadMenuPicture(
        pathString: String = "pic",
        menuId: String,
        byteArray: ByteArray,
    ): Unit?

    suspend fun uploadMenuDishPicture(
        pathString: String = "dish",
        menuId: String,
        dishId: String,
        byteArray: ByteArray,
    ): Unit?

    suspend fun downloadMenuPictureUri(
        pathString: String = "pic",
        menuId: String,
    ): Uri?

    suspend fun downloadMenuDishPictureUri(
        pathString: String = "dish",
        menuId: String,
        dishId: String,
    ): Uri?
}