package com.aplikasijagad

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.aplikasijagad.database.Loket
import com.aplikasijagad.database.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detail_order.*

class DetailOrderActivity : AppCompatActivity() {

    private lateinit var orderId: String
    private lateinit var auth: FirebaseAuth
    private lateinit var listOrder: MutableList<Order>
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        listOrder = mutableListOf()
        orderId = database.getReference("ORDER").push().key!!

        val data = intent.getParcelableExtra<Order>("data")
        detail_kode.text = data?.uidorder.toString()
        detail_nama_pengirim.text = data?.namaPenerima
        detail_no_pengirim.text = data?.noPengirim
        detail_nama_penerima.text = data?.namaPenerima
        detail_no_penerima.text = data?.noPenerima
        detail_alamat_penerima.text = data?.alamat
        detail_rincian_waktu.text = data?.waktu
        detail_rincian_tanggal.text = data?.tanggal
        detail_rincian_berat.text = data?.berat
        detail_rincian_harga.text = data?.harga
        detail_rincian_status.text = data?.status
        detail_rincian_kurir.text = data?.kurir
    }

//    private fun accepted() {
//        val builder = AlertDialog.Builder(requireContext())
//        val view = layoutInflater.inflate(R.layout.accepted, null)
//        builder.setView(view)
//        val dialog = builder.show()
//
//        view.button_logout.setOnClickListener {
//            dialog.dismiss()
//            auth.signOut()
//            val intent = Intent(requireActivity(), MainActivity::class.java)
//            startActivity(intent)
//        }
//
//        view.close_builders.setOnClickListener {
//            dialog.dismiss()
//        }
//    }
//
//    private fun rejected() {
//        val builder = AlertDialog.Builder(requireContext())
//        val view = layoutInflater.inflate(R.layout.rejected, null)
//        builder.setView(view)
//        val dialog = builder.show()
//
//        view.button_logout.setOnClickListener {
//
//        }
//
//        view.close_builders.setOnClickListener {
//            dialog.dismiss()
//        }
//    }
}