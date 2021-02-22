package com.aplikasijagad.add

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aplikasijagad.R
import com.aplikasijagad.database.Order
import com.aplikasijagad.databinding.ActivityAddOrderBinding
import com.aplikasijagad.models.Users
import com.google.firebase.database.*
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_add_order.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class add_order : AppCompatActivity(), FirebaseLoadData {
    private lateinit var binding: ActivityAddOrderBinding
    lateinit var ref: DatabaseReference
    private val STORAGE_CODE: Int = 100
    private var spinner: Spinner? = null
    var arrayList: ArrayList<String> = ArrayList()

    lateinit var kurirRef: DatabaseReference
    lateinit var FirebaseLoadData: FirebaseLoadData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_order)
        ref = FirebaseDatabase.getInstance().getReference("ORDER")
        kurirRef = FirebaseDatabase.getInstance().getReference("Users")

        FirebaseLoadData = this
        kurirRef.addValueEventListener(object : ValueEventListener{
            var kurirList:MutableList<Users> = ArrayList<Users>()
            override fun onCancelled(error: DatabaseError) {
                FirebaseLoadData.onFirebaseLoadFailed(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (kurirSnapshot in snapshot.children)
                    kurirSnapshot.getValue<Users>(Users::class.java!!)?.let { kurirList.add(it) }
                FirebaseLoadData.onFirebaseLoadSuccess(kurirList)
            }

        })
        onItemSelectedstatus()

        spinner = findViewById(R.id.spinKurir)
//        showDataSpinner()
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

    override fun onFirebaseLoadSuccess(kurirList: List<Users>) {
        val kurir_name_title = getKurirNameList(kurirList)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kurir_name_title)

        spinKurir.adapter = adapter
    }

    private fun getKurirNameList(kurirList: List<Users>): List<String> {
        val result = ArrayList<String>()
        for (Users in kurirList)
            result.add(Users.name!!)
        return result

    }

    override fun onFirebaseLoadFailed(message: String) {
        TODO("Not yet implemented")
    }

}
