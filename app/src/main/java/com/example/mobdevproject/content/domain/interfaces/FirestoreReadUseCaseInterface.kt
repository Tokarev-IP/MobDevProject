package com.example.mobdevproject.content.domain.interfaces

import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.data.MyMenuId

interface FirestoreReadUseCaseInterface {

    suspend fun checkMyMenu(
        collection: String = "id",
        userId: String,
    ): MyMenuId?

    suspend fun getMenuMainData(
        collection: String = "menu",
        menuId: String,
    ): MenuMainData?

    suspend fun getMenuDataList(
        collection1: String = "data",
        collection2: String = "menu",
        menuId: String,
    ): List<MenuData>

}