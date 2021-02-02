package com.aplikasijagad.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aplikasijagad.DashboardAdmin
import com.aplikasijagad.R
import com.aplikasijagad.database.Loket
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_loket.*

class add_loket : AppCompatActivity() {
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_loket)
        ref = FirebaseDatabase.getInstance().getReference("LOKET")
        home.setOnClickListener { startActivity(Intent(this, DashboardAdmin::class.java)) }
        btn_simpanloket.setOnClickListener {
            when {
                ed_nmPenerima.text.isEmpty() -> {
                    ed_nmPenerima.error = "Input nama penerima tidak boleh kososng"
                    return@setOnClickListener
                }
                ed_almtAsal.text.isEmpty() -> {
                    ed_almtAsal.error = "input alamat asal tidak boleh kosong"
                    return@setOnClickListener
                }
                ed_kecAsal.text.isEmpty() -> {
                    ed_kecAsal.error = "Input kecamatan asal Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_kabAsal.text.isEmpty() -> {
                    ed_kabAsal.error = "Input  kabupaten asal Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_provAsal.text.isEmpty() -> {
                    ed_provAsal.error = "Input  Provinsi asal Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_almtTujuan.text.isEmpty() -> {
                    ed_almtTujuan.error = "Input alamat tujuan Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_kacTujuan.text.isEmpty() -> {
                    ed_kacTujuan.error = "Input  kecamatan tujuan Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_kabTujuan.text.isEmpty() -> {
                    ed_kabTujuan.error = "Input  kabupaten tujuan Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_provTujuan.text.isEmpty() -> {
                    ed_provTujuan.error = "Input provinsis tujuan Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_kodePosTujuan.text.isEmpty() -> {
                    ed_kodePosTujuan.error = "Input kode pos tujuan Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_beratBarang.text.isEmpty() -> {
                    ed_beratBarang.error = "Input berat barang Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                else -> {
                    savedata()
                }
            }

        }
    }

    private fun savedata() {
        //val id =
        val nama = ed_nmPenerima.text.toString()
        val alamat_asal = ed_almtAsal.text.toString()
        val kecamatan_asal = ed_kecAsal.text.toString()
        val kabupaten_asal = ed_kabAsal.text.toString()
        val provinsis_asal = ed_provAsal.text.toString()
        val kodepos_asal = ed_kodePos.text.toString()
        val alamat_tujuan = ed_almtTujuan.text.toString()
        val kecamatan_tujuan = ed_kacTujuan.text.toString()
        val kabupaten_tujuan = ed_kabTujuan.text.toString()
        val provinsi_tujuan = ed_provTujuan.text.toString()
        val kodepos_tujuan = ed_kodePosTujuan.text.toString()
        val berat_barang = ed_beratBarang.text.toString()

        val loket = Loket(
            nama,
            alamat_asal,
            kecamatan_asal,
            kabupaten_asal,
            provinsis_asal,
            kodepos_asal,
            alamat_tujuan,
            kecamatan_tujuan,
            kabupaten_tujuan,
            provinsi_tujuan,
            kodepos_tujuan,
            berat_barang
        )
        val loketid = ref.push().key.toString()

        ref.child(loketid).setValue(loket).addOnCompleteListener {
            Toast.makeText(this, "data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            ed_nmPenerima.setText("")
            ed_almtAsal.setText("")
            ed_kecAsal.setText("")
            ed_kabAsal.setText("")
            ed_provAsal.setText("")
            ed_kodePos.setText("")
            ed_almtTujuan.setText("")
            ed_kacTujuan.setText("")
            ed_kabTujuan.setText("")
            ed_provTujuan.setText("")
            ed_kodePosTujuan.setText("")
            ed_beratBarang.setText("")
        }
    }
}