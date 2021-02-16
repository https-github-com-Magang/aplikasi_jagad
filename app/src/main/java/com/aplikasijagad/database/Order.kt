package com.aplikasijagad.database

data class Order(
    val namaPengirim : String,
    val noPengirim : String,
    val namaPenerima : String,
    val noPenerima : String,
    val alamat : String,
    val berat :String,
    val harga:String,
    val status :String,
    val kurir: String,
    val orderId: String,
    val kurirId: String
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
            ""


        )

}