package com.aplikasijagad.database

import androidx.room.Entity


data class Order(
    val uidorder:Int?=null,
    val uiduser:String="",
    val namaPengirim : String="",
    val noPengirim : String="",
    val namaPenerima : String="",
    val noPenerima : String="",
    val alamat : String="",
    val berat :String="",
    val harga:String="",
    val status :String="",
    val kurir: String
){ constructor():
        this(
            null,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""






        )

}