package com.aplikasijagad.add

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
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
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_add_order.*
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class add_order : AppCompatActivity() {
    private lateinit var binding: ActivityAddOrderBinding
    lateinit var ref: DatabaseReference
    private val STORAGE_CODE: Int = 100;


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
                    savePdf()
                }
            }
        }

//        btn_addOrder.setOnClickListener {
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
//                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_DENIED){
//
//                    val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    requestPermissions(permission, STORAGE_CODE)
//
//                    }
//                else{
//                    savePdf()
//                }
//            }
//            savePdf()
//        }
    }

    private fun savePdf(){

        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            mDoc.open()

            val namaPengirim = ed_nmPengirim.text.toString()
            val noPengirim = ed_noPengirim.text.toString()
            val namaPenerima = ed_nmPenerima.text.toString()
            val noPenerima = ed_noPenerima.text.toString()
            val alamat = ed_almtPenerima.text.toString()
            val berat = ed_beratBarang.text.toString()
            val harga =ed_harga.text.toString()
            val status = binding.spinStatus.selectedItem.toString()
            val kurir = binding.spinKurir.selectedItem.toString()

            mDoc.addAuthor("Data Order")
            mDoc.add(Paragraph( "Data Order \n \n" +
                "Nama Pengirim = " + namaPengirim + "\n" +
                    "No pengirim = " + noPengirim + "\n" +
                    "Nama Penerima = " + namaPenerima + "\n" +
                    "No Penerma = "  + noPenerima + "\n" +
                    "Alamat = " + alamat + "\n" + "Berat = " + berat + "\n" +
                    "Harga = " + harga + "\n" +
                    "Status = " + status + "\n" +
                    "Kurir = "+ kurir))

            mDoc.close()

            Toast.makeText(this, "$mFileName.pdf\n is saved to \n $mFilePath", Toast.LENGTH_SHORT)
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePdf()
                }
                else{
                    Toast.makeText(this, "Permission Denied....", Toast.LENGTH_SHORT)
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
        val options_kurir = arrayOf("padhisa", "vira", "veronika")
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
