package com.aplikasijagad.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aplikasijagad.R
import com.aplikasijagad.database.Order
import com.aplikasijagad.databinding.ActivityAddOrderBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_order.*

class add_order : AppCompatActivity() {
    private lateinit var binding: ActivityAddOrderBinding
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_order)
        ref = FirebaseDatabase.getInstance().getReference("ORDER")
        onItemSelectedstatus()
        onItemSelectedkurir()
        btn_addOrder.setOnClickListener {
            when {
                ed_nmPengirim.text.isEmpty() -> {
                    ed_nmPengirim.error = "Nama Pengirim Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_noPengirim.text.isEmpty() -> {
                    ed_noPengirim.error = "No Telp Pengirim Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_nmPenerima.text.isEmpty() -> {
                    ed_nmPenerima.error = "Nama Penerima Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_noPenerima.text.isEmpty() -> {
                    ed_noPenerima.error = "No Telp Penerima Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_beratBarang.text.isEmpty() -> {
                    ed_beratBarang.error = "Berat Barang Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                ed_harga.text.isEmpty() -> {
                    ed_harga.error = "Harga tidak boleh kosong"
                    return@setOnClickListener
                }
                else -> {
                    saveDataOrder()
                }
            }
        }
    }


    private fun saveDataOrder() {
        val namaPengirim = ed_nmPengirim.text.toString()
        val noPengirim = ed_noPengirim.text.toString()
        val namaPenerima = ed_nmPenerima.text.toString()
        val noPenerima = ed_noPenerima.text.toString()
        val alamat = ed_almtPenerima.text.toString()
        val berat = ed_beratBarang.text.toString()
        val harga =ed_harga.text.toString()
        val status = binding.spinStatus.selectedItem.toString()
        val kurir = binding.spinKurir.selectedItem.toString()

        val order = Order(
            namaPengirim ,
         noPengirim,
         namaPenerima,
         noPenerima,
         alamat,
         berat,
         harga,
        //val waktu : String,
        //val tanggal : String,
         status,
         kurir
        )
        val orderid=ref.push().key.toString()
        ref.child(orderid).setValue(order).addOnCompleteListener(){
            Toast.makeText(this,"data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            ed_nmPengirim.setText("")
            ed_noPengirim.setText("")
            ed_nmPenerima.setText("")
            ed_noPenerima.setText("")
            ed_almtPenerima.setText("")
            ed_beratBarang.setText("")
            ed_harga.setText("")
            binding.spinStatus.selectedItem
            binding.spinKurir.selectedItem

        }
    }

    private fun onItemSelectedstatus() {
        val option_order = binding.spinStatus
        val options_order = arrayOf("Lunas", "Belum Lunas")
        option_order.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options_order)
        option_order.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
            }
        }

    }

    private fun onItemSelectedkurir() {
        val option_kurir = binding.spinKurir
        val options_kurir = arrayOf("phadisa", "vira", "veronika")
        option_kurir.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options_kurir)
        option_kurir.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
            }
        }

    }
}
