package com.example.mobdevproject.content.domain.interfaces

import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData

interface FirestoreAddUseCaseInterface {

    fun addMyMenuId(
        collection: String = "id",
        userId: String,
        menuId: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String) -> Unit
    )

    suspend fun addMenuMainData(
        collection: String = "menu",
        data: MenuMainData,
        menuId: String,
    ): Unit?

    suspend fun addMenuData(
        collection1: String = "data",
        collection2: String = "menu",
        data: MenuData,
        menuId: String,
        documentId: String,
    ): Unit?
}