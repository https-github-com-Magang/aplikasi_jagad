package com.aplikasijagad.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Amplop(
    var uidSRJ: String ="",
    var uidDriver: String="",
    var berat: String = "",
    var jenisamplop: String = "",
    var noamplop: String = "",
    var penerima: String = "",
    var pengirim: String = ""

) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )
}
