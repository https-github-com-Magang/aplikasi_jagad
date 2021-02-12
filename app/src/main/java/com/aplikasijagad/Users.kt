package com.aplikasijagad

data class Users(
    var uid: String = "",
    var name: String = "",
    var nik: String = "",
    var email: String = "",
    var password: String = "",
    var phone: String = "",
    var usertype: String = "",
    val lat: Double? = null,
    val lng: Double? = null
) {
    constructor() : this("", "", "", "", "",
    "", "", null, null, )
}