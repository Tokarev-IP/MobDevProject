package com.example.mobdevproject.content.data

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable

interface FirestoreData

data class MyMenuId @JvmOverloads constructor(
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String = "",
) : FirestoreData

@Serializable
data class MenuMainData @JvmOverloads constructor(
    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String = "",

    @get:PropertyName("pictureUri")
    @set:PropertyName("pictureUri")
    var pictureUri: String? = null,

    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String = "",
) : FirestoreData

@Serializable
data class MenuData @JvmOverloads constructor(
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String = "",

    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String = "",

    @get:PropertyName("price")
    @set:PropertyName("price")
    var price: Double = 0.0,

    @get:PropertyName("pictureUri")
    @set:PropertyName("pictureUri")
    var pictureUri: String? = null,
) : FirestoreData