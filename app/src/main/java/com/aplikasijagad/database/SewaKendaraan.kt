package com.aplikasijagad.database

data class SewaKendaraan(
    val nama_pengirim_sewa: String,
    val no_Ktp_sewa: String,
    val kendaraan_sewa: String,
    val hari_sewa: String,
    val deskripsi_hari: String
) {
    constructor() :
            this(
                "",
                "",
                "",
                "",
                ""

            )
}