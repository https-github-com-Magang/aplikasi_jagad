package com.aplikasijagad.add

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aplikasijagad.R
import com.aplikasijagad.database.Loket
import com.aplikasijagad.database.Sewa
import com.aplikasijagad.databinding.ActivityAddSewaBinding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.view.View
import kotlinx.android.synthetic.main.activity_add_sewa.*



class Add_Sewa : AppCompatActivity() {
    private lateinit var binding: ActivityAddSewaBinding
    lateinit var ref: DatabaseReference
    private var status: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sewa)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_sewa)
        ref = FirebaseDatabase.getInstance().getReference("LOKET")
        onItemSelectedkendaraan()
        saveDataSewa()
    }

    private fun saveDataSewa() {
        val nama_pengirim_sewa = ed_nama.text.toString()
        val no_Ktp_sewa = ed_noKtp.text.toString()
        //val kendaraan_sewa =spinner2.getItemAtPosition().toString()
        val hari_sewa = ed_jmlhHari.text.toString()
        val deskripsi_hari = deskripsi.text.toString()

        val sewa = Sewa(
            nama_pengirim_sewa,
            no_Ktp_sewa,
            hari_sewa,
            deskripsi_hari
        )
        val sewaid = ref.push().key.toString()
        ref.child(sewaid).setValue(sewa).addOnCompleteListener {
            Toast.makeText(this, "data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            ed_nama.setText("")
            ed_noKtp.setText("")
            ed_jmlhHari.setText("")
            deskripsi.setText("")
        }
    }

    private fun onItemSelectedkendaraan() {
        val option = binding.spinner2
        val options = arrayOf("jenis kendaraan", "motor", "mobil")

        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)
        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                if (options[position] == "jenis kendaraan") {
                    status = ""
                }
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
}





