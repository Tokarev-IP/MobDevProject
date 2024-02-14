package com.example.mobdevproject.content.domain.interfaces

import com.example.mobdevproject.content.data.MyMenuId

interface FirestoreReadUseCaseInterface {

    fun checkMyMenu(
        collection: String = "id",
        userId: String,
        onResult: (document: MyMenuId) -> Unit,
        onEmpty: () -> Unit,
        onFailure: (msg: String) -> Unit,
    )

}