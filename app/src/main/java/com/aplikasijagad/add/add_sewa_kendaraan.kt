package com.aplikasijagad.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import com.aplikasijagad.R
import com.aplikasijagad.database.Kendaraan
import com.aplikasijagad.database.Sewa
import com.aplikasijagad.database.SewaKendaraan
import com.aplikasijagad.databinding.ActivityAddSewaBinding
import com.aplikasijagad.databinding.ActivityAddSewaKendaraanBinding
import com.aplikasijagad.models.Users
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_order.*
import kotlinx.android.synthetic.main.activity_add_sewa.*
import kotlinx.android.synthetic.main.activity_add_sewa.deskripsi
import kotlinx.android.synthetic.main.activity_add_sewa.ed_jmlhHari
import kotlinx.android.synthetic.main.activity_add_sewa.ed_nama
import kotlinx.android.synthetic.main.activity_add_sewa.ed_noKtp
import kotlinx.android.synthetic.main.activity_add_sewa_kendaraan.*

class add_sewa_kendaraan : AppCompatActivity(), FirebaseLoadNopol {
    private lateinit var binding: ActivityAddSewaKendaraanBinding
    lateinit var ref: DatabaseReference
    private var status: String = ""
    var arrayList: ArrayList<String> = ArrayList()

    private var spinner: Spinner? = null
    lateinit var nopolRef: DatabaseReference
    lateinit var FirebaseLoadNopol: FirebaseLoadNopol


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sewa_kendaraan)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_sewa_kendaraan)
        ref = FirebaseDatabase.getInstance().getReference("SEWA")
        nopolRef = FirebaseDatabase.getInstance().getReference("Kendaraan")

        FirebaseLoadNopol = this
        nopolRef.addValueEventListener(object : ValueEventListener{
            var nopolList:MutableList<Kendaraan> = ArrayList<Kendaraan>()

            override fun onCancelled(error: DatabaseError) {
                FirebaseLoadNopol.onFirebaseNopolFailed(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (nopolSnapshot in snapshot.children)
                    nopolSnapshot.getValue<Kendaraan>(Kendaraan::class.java!!)?.let { nopolList.add(it) }
                FirebaseLoadNopol.onFirebaseNopolSuccess(nopolList)
            }
        })

        btnSewaKendaraan.setOnClickListener {
            when {
                ed_nama.text.isEmpty() -> {
                    ed_nama.error = "Input nama tidak boleh kososng"
                    return@setOnClickListener
                }
                ed_noKtp.text.isEmpty() -> {
                    ed_noKtp.error = "input no KTP tidak boleh kosong"
                    return@setOnClickListener
                }

                ed_jmlhHari.text.isEmpty() -> {
                    ed_jmlhHari.error = "Input jumlah hari Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                deskripsi.text.isEmpty() -> {
                    deskripsi.error = "deskripsi Tidak Boleh Kosong"
                    return@setOnClickListener
                }
                else -> {
                    saveDataSewa()
                }
            }

        }
        onItemSelectedkendaraan()
        spinner = findViewById(R.id.spin_noPolisi)

    }
    private fun saveDataSewa() {
        val nama_pengirim_sewa = ed_nama.text.toString()
        val no_Ktp_sewa = ed_noKtp.text.toString()
        val kendaraan_sewa =binding.spinner2.selectedItem.toString()
        val noPolisi = binding.spinNoPolisi.selectedItem.toString()
        val hari_sewa = ed_jmlhHari.text.toString()
        val deskripsi_hari = deskripsi.text.toString()

        val sewa = SewaKendaraan(
            nama_pengirim_sewa,
            no_Ktp_sewa,
            kendaraan_sewa,
            noPolisi,
            hari_sewa,
            deskripsi_hari

        )
        val sewaid = ref.push().key.toString()
        ref.child(sewaid).setValue(sewa).addOnCompleteListener {
            Toast.makeText(this, "data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            ed_nama.setText("")
            ed_noKtp.setText("")
            binding.spinner2.selectedItem
            binding.spinNoPolisi.selectedItem
            ed_jmlhHari.setText("")
            deskripsi.setText("")
        }
    }


    private fun onItemSelectedkendaraan() {
        val option = binding.spinner2
        val options = arrayOf( "Motor", "Mobil")

        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)
        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                if (options[position] == "motor") {
                    val item = parent?.getItemAtPosition(position).toString()
                    status = item
                }
                if (options[position] == "mobil") {
                    val item = parent?.getItemAtPosition(position).toString()
                    status = item
                }
            }
        }
    }

    override fun onFirebaseNopolSuccess(nopolList: List<Kendaraan>) {
        val nopol_name_title = getNopolNameList(nopolList)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nopol_name_title)

        spin_noPolisi.adapter = adapter
    }

    private fun getNopolNameList(nopolList: List<Kendaraan> ): List<String> {
        val result = ArrayList<String>()
        for (Kendaraan in nopolList)
            result.add(Kendaraan.no_polisi!!)
        return result

    }

    override fun onFirebaseNopolFailed(message: String) {
        TODO("Not yet implemented")
    }


}