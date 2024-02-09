package com.example.mobdevproject.additional

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
