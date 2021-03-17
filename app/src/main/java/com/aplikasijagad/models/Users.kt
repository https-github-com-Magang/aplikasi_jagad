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
    val lat: Double? = null,
    val lng: Double? = null
) {
    constructor() : this(
        "", "", "", "", "",
        "", "", "", null, null,
    )
}

data class Driver(
    var uid: String = "",
    var name: String = ""
) {
    constructor() : this(
        "",
        ""
    )
}

@Parcelize
data class SURATJALAN(
    var uidSRJ: String ="",
    var driver: String = "",
    var tujuan: String = "",
    var tanggal: String = ""

): Parcelable {
    constructor() : this(
        "",
        "",
        "",
        "",
    )
}
