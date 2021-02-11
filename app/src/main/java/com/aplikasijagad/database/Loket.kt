package com.aplikasijagad.database

data class Order (
    val namaPengirim :String,
    val noPengirim :String,
    val namaPenerima:String,
    val noPenerima:String,
    val alamat:String,
    val waktu:String,
    val tanggal:String,
    val status:String,
    val kurir:String,
){
    constructor():
            this(
                "",
                "",
                "",
                "",
                "",
                "13:52",
                "dd/mm/yy",
                "",
                ""
            )
}