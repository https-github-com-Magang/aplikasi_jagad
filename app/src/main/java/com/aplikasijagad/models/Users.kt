package com.aplikasijagad.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Users(
    var uid: String = "",
    var name: String = "",
    var nik: String = "",
    var email: String = "",
    var password: String = "",
    var phone: String = "",
    var address: String = "",
    var usertype: String = "",
    val lat: Double?,
    val lng: Double?
) {
    constructor() : this(
        "", "", "", "", "",
        "", "", "", null, null,
    )
}

data class DRIVER(
    var uidDriver: String = "",
//    var uidSRJ: String="",
    var name: String="",
    val lat: Double?,
    val lng: Double?

) {
    constructor() : this(
        "",
        "",
        null,
        null
    )
}

@Parcelize
data class SURATJALAN(
    var uidSRJ: String ="",
    var driver: String = "",
    var tujuan: String = "",
    var tanggal: String = "",
    var uidDriver: String ="",
    var berat: String = "",
    var jenisamplop: String = "",
    var noamplop: String = "",
    var penerima: String = "",
    var pengirim: String = ""

): Parcelable {
    constructor() : this(
        "",
        "",
        "",
        "",
        "","",
        "", "","", ""
    )
}
