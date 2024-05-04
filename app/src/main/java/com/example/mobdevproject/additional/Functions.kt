package com.example.mobdevproject.additional

import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun String.isEmail(): Boolean {
    var amount = 0
    for (letter in this) {
        if (letter == '@') amount++
    }
    return amount == 1
}

fun String.isPhoneNumber(): Boolean {
    return this.length > 6
}
