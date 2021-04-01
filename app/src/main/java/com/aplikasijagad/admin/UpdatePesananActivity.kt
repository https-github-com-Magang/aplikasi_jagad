package com.aplikasijagad.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasijagad.AdapterUtil
import com.aplikasijagad.DetailOrderActivity

import com.aplikasijagad.R
import com.aplikasijagad.databinding.ActivityUpdatePesananBinding
import com.aplikasijagad.models.Amplop
import com.aplikasijagad.models.SURATJALAN
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update_pesanan.*
import kotlinx.android.synthetic.main.activity_update_pesanan.view.*
import kotlinx.android.synthetic.main.list_amplop.*
import kotlinx.android.synthetic.main.list_amplop.view.*
import kotlinx.android.synthetic.main.list_suratjalan.view.*

class UpdatePesananActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdatePesananBinding
    lateinit var ref: DatabaseReference
    lateinit var amplopList: MutableList<SURATJALAN>
    private lateinit var adapter: AdapterUtil<SURATJALAN>
    lateinit var searchtext:EditText
    lateinit var recyclerView: RecyclerView
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        amplopList = mutableListOf()
        database = FirebaseDatabase.getInstance()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_pesanan)

//        binding.btCariData.setOnClickListener {
//            if (ed_searchAmplop.text.isEmpty()){
//                Toast.makeText(applicationContext, "Inputan tidak boleh kosong!!", Toast.LENGTH_SHORT)
//            }else{
//                cariAmplop()
//            }
//        }
//
//        recyclerView = findViewById(R.id.rvListAmplop)



        database.getReference("SURATJALAN").orderByChild("uiDriver").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (eventSnapshot in p0.children) {
                        val data = eventSnapshot.getValue(SURATJALAN::class.java)
                        data?.let {
                            amplopList.add(it)
                        }
                    }
                }

                adapter = AdapterUtil(R.layout.list_suratjalan, amplopList, { itemView, item ->
                    itemView.tv_noSTB.text = item.uidSRJ
                    itemView.tv_tglsurat.text = item.tanggal
                    itemView.tv_Tujuan.text = item.tujuan
                    itemView.tv_drive.text = item.driver
                }, { _, item ->
                    val intent = Intent(this@UpdatePesananActivity, DetailOrderActivity::class.java)
                    intent.putExtra("data", item)
                    startActivity(intent)
                })

                rvListAmplop.apply {
                    adapter = this@UpdatePesananActivity.adapter
                    layoutManager = LinearLayoutManager(context)
                }
            }
        })

    }

    private fun cariAmplop() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.list_amplop, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Data Found!")

        val mAlertDialog = mBuilder.show()

        val tv_rincian1 = mDialogView.findViewById<TextView>(R.id.tv_rincian1)


        FirebaseDatabase.getInstance().reference
            .child("Amplop")
            .orderByChild("noamplop").equalTo(ed_searchAmplop.text.toString().trim())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.children.first().value as Map<String, String>
                    tv_rincian1.text = map["noamplop"].toString()

                }
            })
    }

}