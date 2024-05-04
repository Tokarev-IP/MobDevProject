package com.example.mobdevproject.content.domain.interfaces

import com.example.mobdevproject.content.data.MenuMainData

interface FirestoreSearchUseCaseInterface {
    suspend fun getAllMenu(
        collection: String = "menu",
    ): List<MenuMainData>
    suspend fun getMenuByName(
        name: String,
        collection: String = "menu"
    ): List<MenuMainData>
}