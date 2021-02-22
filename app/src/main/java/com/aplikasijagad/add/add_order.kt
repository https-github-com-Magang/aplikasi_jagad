package com.aplikasijagad.add

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aplikasijagad.R
import com.aplikasijagad.database.Order
import com.aplikasijagad.databinding.ActivityAddOrderBinding
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_order.*
import kotlinx.android.synthetic.main.fragment_home_admin.*


class add_order : AppCompatActivity() {

    private lateinit var binding: ActivityAddOrderBinding
    lateinit var ref: DatabaseReference
    private lateinit var namadriver: DatabaseReference
    private lateinit var database:DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var status: String = ""
    private var countId: Int = 0
    var maxid: String = ""
    var maxidorder :Int=0

    private var currentUser: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_order)
        ref = FirebaseDatabase.getInstance().getReference("ORDER")
        database = FirebaseDatabase.getInstance().getReference("Users")
        //namadriver = FirebaseDatabase.getInstance().getReference("DRIVER")
        onItemSelectedstatus()
        onItemSelectedkurir()
        //dataload()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // get total available quest
                if (dataSnapshot.exists()) {
                   // maxid = dataSnapshot.childrenCount.toString()
                    maxidorder=dataSnapshot.childrenCount.toInt()

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        database.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentUser=dataSnapshot.childrenCount.toString()

                }

            }

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


//    private fun dataload() {
//        val uid = auth.currentUser!!.uid
//        namadriver.orderByChild("uid").equalTo(uid)
//            .addValueEventListener(object : ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//                    if (p0.exists()) {
//                        for (userSnapshot in p0.children) {
//                            val data = userSnapshot.getValue(Users::class.java)
//                            //data?.let {listUsers.add(it)}
//                            //.text = data!!.name
//
//                        }
//                    }
//                }
//            })
//    }
//    private fun tampildata(){
//        val ambilnamakurir = namadriver.child("DRIVER")
//            .orderByChild("name").equalTo(spinKurir.toString())
//        namadriver.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("continenterror","error on name")
//            }
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (ds in dataSnapshot.children) {
//                    Log.d("spinner brand", spinKurir.selectedItem.toString())
//                    val name = ds.child("name").getValue(String::class.java)
//                    val nameselected = name.toString()
//
//                    Log.d("name", nameselected)
//                }
//            }
//        })
//    }

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
        val uidorder = maxidorder!!
        val uiduser = currentUser!!


        val order = Order(
            uidorder,
            uiduser,
            namaPengirim,
            noPengirim,
            namaPenerima,
            noPenerima,
            alamat,
            berat,
            harga,
            status,
            kurir
        )
//        ref.child("uid").setValue(order).addOnCompleteListener(){
//            Toast.makeText(this, "data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
//        }

        val objek = "order"
        val orderid = ref.push().key.toString()
        ref.child((objek+maxidorder)).setValue(order).addOnCompleteListener() {
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

        }.addOnFailureListener() {
            Toast.makeText(this, "data gagal ditambahkan", Toast.LENGTH_SHORT).show()
        }
//        ref.child(uid).setValue(order)
//            .addOnFailureListener { e ->
//                Toast.makeText(this, "data gagal ditambahkan", Toast.LENGTH_SHORT).show()
//            }
//            .addOnSuccessListener {
//                Toast.makeText(this, "data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
//
//
//                val orderid = ref.push().key.toString()
//                ref.child(orderid).setValue(order).addOnCompleteListener() {
//                    Toast.makeText(this, "data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
//                }
//            }
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
                val item = parent?.getItemAtPosition(position).toString()
                status = item
            }
        }

    }
}

