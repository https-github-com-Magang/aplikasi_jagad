package com.aplikasijagad.database

data class Order(
    val uidorder: String,
    val uiduser: String,
    val namaPengirim: String,
    val noPengirim: String,
    val namaPenerima: String,
    val noPenerima: String,
    val alamat: String,
    val berat:String,
    val harga:String,
    val status:String,
    val kurir: String,
    val tanggal: String,
    val waktu: String

){ constructor():
        this(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "18-02-2021",
            "15:44"


        )

}