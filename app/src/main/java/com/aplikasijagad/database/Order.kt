package com.aplikasijagad.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val namaPengirim : String = "",
    val noPengirim : String = "",
    val namaPenerima : String = "",
    val noPenerima : String = "",
    val alamat : String = "",
    val berat :String = "",
    val harga:String = "",
    val status :String = "",
    val kurir: String = "",
    val orderId: String = "",
    val kurirId: String = ""
) : Parcelable