package com.aplikasijagad.add

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.aplikasijagad.R
import com.aplikasijagad.database.Order
import com.aplikasijagad.databinding.ActivityAddOrderBinding
import com.aplikasijagad.models.Driver
import com.aplikasijagad.models.Users
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_order.*
import java.text.SimpleDateFormat

class add_order : AppCompatActivity(),FirebaseLoadData,FirebaseLoadIdKurir {
    private lateinit var binding: ActivityAddOrderBinding
    private lateinit var orderId: String
    lateinit var ref: DatabaseReference
    lateinit var kurirRef: DatabaseReference
    lateinit var datauser: DatabaseReference
    private var spinner: Spinner? = null
    var maxidorder :String =""
    var arrayList: ArrayList<String> = ArrayList()
    private lateinit var database:DatabaseReference
    lateinit var FirebaseLoadData: FirebaseLoadData


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_order)
        ref = FirebaseDatabase.getInstance().getReference("ORDER")
        kurirRef = FirebaseDatabase.getInstance().getReference("DRIVER")
        datauser = FirebaseDatabase.getInstance().getReference("Users")
        onItemSelectedstatus()
        //onItemSelectedkurir()

        //SPINNER GET DATA KURIR
        FirebaseLoadData = this
        kurirRef.addValueEventListener(object : ValueEventListener {
            var kurirList:MutableList<Driver> = ArrayList<Driver>()
            override fun onCancelled(error: DatabaseError) {
                FirebaseLoadData.onFirebaseLoadFailed(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (kurirSnapshot in snapshot.children)
                    kurirSnapshot.getValue<Driver>(Driver::class.java!!)?.let { kurirList.add(it) }
                FirebaseLoadData.onFirebaseLoadSuccess(kurirList)
            }

        })
        spinner = findViewById(R.id.spinKurir)

        //UBAH ID
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // get total available quest
                if (dataSnapshot.exists()) {
                    // maxid = dataSnapshot.childrenCount.toString()
                    maxidorder=dataSnapshot.childrenCount.toString()


                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
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


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    private fun saveDataOrder() {
        val namaPengirim = ed_nmPengirim.text.toString()
        val noPengirim = ed_noPengirim.text.toString()
        val namaPenerima = ed_nmPenerima.text.toString()
        val noPenerima = ed_noPenerima.text.toString()
        val alamat = ed_almtPenerima.text.toString()
        val berat = ed_beratBarang.text.toString()
        val harga = ed_harga.text.toString()
        val status = binding.spinStatus.selectedItem.toString()
        val kurir = binding.spinKurir.selectedItem.toString()
       // val tanggal = SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().time)
        //val waktu = SimpleDateFormat("HH:mm").format(Calendar.getInstance().timeZone)
        val uidorder =maxidorder!!

        val order = Order(
            uidorder,
            namaPengirim,
            noPengirim,
            namaPenerima,
            noPenerima,
            alamat,
            berat,
            harga,
            status,
            kurir
            //tanggal,
           // waktu
        )
        val orderid = ref.push().key.toString()
        //val objek="order"
        ref.child(orderid).setValue(order).addOnCompleteListener() {
            Toast.makeText(this, "data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
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
    private fun getIdKurir(){
        datauser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {


                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
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
    override fun onFirebaseLoadSuccess(kurirList: List<Driver>) {
        val kurir_name_title = getKurirNameList(kurirList)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kurir_name_title)

        spinKurir.adapter = adapter
    }

    private fun getKurirNameList(kurirList: List<Driver>): List<String> {
        val result = ArrayList<String>()
        for (Driver in kurirList)
            result.add(Driver.name!!)
        return result

    }

    override fun onFirebaseLoadFailed(message: String) {
        TODO("Not yet implemented")
    }
    private fun getIdKurirList(IdkurirList: List<Driver>): List<String> {
        val resultIdKurir = ArrayList<String>()
        for (Driver in IdkurirList)
            resultIdKurir.add(Driver.uidriver!!)
        return resultIdKurir

    }

    override fun onFirebaseLoadIdKurirSuccess(IDKurir: List<Driver>) {
        val getidkurir=getIdKurirList(IDKurir)

    }

    override fun onFirebaseLoadIdKurirFailed(message: String) {
        TODO("Not yet implemented")
    }
}
