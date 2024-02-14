package com.example.mobdevproject.content.data

import com.google.firebase.firestore.PropertyName

interface FirestoreData

data class MyMenuId @JvmOverloads constructor(
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String = "",
) : FirestoreData

data class MenuMainData @JvmOverloads constructor(
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String = "",

    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String = "",

    @get:PropertyName("logo")
    @set:PropertyName("logo")
    var logo: String = "",
) : FirestoreData

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
) : FirestoreData

//data class DishDataFirestore @JvmOverloads constructor(
//    @get:PropertyName("name")
//    @set:PropertyName("name")
//    var name: String = "",
//
//    @get:PropertyName("price")
//    @set:PropertyName("price")
//    var price: Double = 0.0,
//
//    @get:PropertyName("priority")
//    @set:PropertyName("priority")
//    var priority: Int = 0,
//
//    @get:PropertyName("isPicture")
//    @set:PropertyName("isPicture")
//    var isPicture: Boolean = false,
//
//    @get:PropertyName("id")
//    @set:PropertyName("id")
//    var id: String = "",
//
//    @get:PropertyName("typeId")
//    @set:PropertyName("typeId")
//    var typeId: String = "",
//): FirestoreData