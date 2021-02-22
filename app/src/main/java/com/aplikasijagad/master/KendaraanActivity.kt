package com.aplikasijagad.master

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aplikasijagad.R
import com.aplikasijagad.database.Kendaraan
import com.aplikasijagad.databinding.ActivityAddSewaKendaraanBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_sewa.*
import kotlinx.android.synthetic.main.form_kendaraan.*

class KendaraanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSewaKendaraanBinding
    lateinit var ref: DatabaseReference
    private lateinit var idKendaraan: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_kendaraan)
        idKendaraan = FirebaseAuth.getInstance()
//        binding = DataBindingUtil.setContentView(this, R.layout.form_kendaraan)
        ref = FirebaseDatabase.getInstance().getReference("Kendaraan")
        btn_formKendaraan.setOnClickListener {
            when {
                ed_namaKendaraan.text.isEmpty() -> {
                    ed_namaKendaraan.error = "Nama Kendaraan harus diisi"
                    return@setOnClickListener
                }
                ed_warna.text.isEmpty()->{
                    ed_warna.error="Warna harus diisi"
                    return@setOnClickListener
                }ed_noPolisi.text.isEmpty()->{
                    ed_noPolisi.error="wajib diisi"
                return@setOnClickListener
            }
                else->{
                saveDataKendaraan()

            }
            }
        }
    }

    private fun saveDataKendaraan() {
//        val idKendaraan = idKendaraan.currentUser!!.uid
        val namaKendaraan = ed_namaKendaraan.text.toString()
        val warnaKendaraan = ed_warna.text.toString()
        val noPolisi = ed_noPolisi.text.toString()
        val kendaraan = Kendaraan(
//            kendaraanid,
            namaKendaraan,
            warnaKendaraan,
            noPolisi,
        )
        val kendaraanid = ref.push().key.toString()
        ref.child(kendaraanid).setValue(kendaraan).addOnCompleteListener {
            Toast.makeText(this, "data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            ed_namaKendaraan.setText("")
            ed_warna.setText("")
            ed_noPolisi.setText("")
        }
    }
}