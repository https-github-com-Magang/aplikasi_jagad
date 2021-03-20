package com.aplikasijagad.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasijagad.AdapterUtil
import com.aplikasijagad.DetailOrderActivity
//import com.firebase.ui.FirebaseRecyclerAdapter
import com.aplikasijagad.R
import com.aplikasijagad.databinding.ActivityUpdatePesananBinding
import com.aplikasijagad.models.Amplop
import com.aplikasijagad.models.SURATJALAN
import com.firebase.ui.auth.data.model.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_order.*
import kotlinx.android.synthetic.main.activity_update_pesanan.*
import kotlinx.android.synthetic.main.activity_update_pesanan.view.*
import kotlinx.android.synthetic.main.list_amplop.view.*

class UpdatePesananActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdatePesananBinding
    lateinit var ref: DatabaseReference
    lateinit var amplopList: MutableList<Amplop>
    private lateinit var adapter: AdapterUtil<Amplop>
    lateinit var recyclerView: RecyclerView
    private lateinit var database: FirebaseDatabase
    //lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<User , UsersViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amplopList = mutableListOf()
        database = FirebaseDatabase.getInstance()


        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_pesanan)

        binding.btCariData.setOnClickListener {
            if (ed_searchAmplop.text.isEmpty()){
                Toast.makeText(applicationContext, "Inputan tidak boleh kosong!!", Toast.LENGTH_SHORT)
            }else{
                cariAmplop()
            }
        }



        database.getReference("Amplop").orderByChild("uiDriver").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (eventSnapshot in p0.children) {
                            val data = eventSnapshot.getValue(Amplop::class.java)
                            data?.let {
                                amplopList.add(it)
                            }
                        }
                    }

                    adapter = AdapterUtil(R.layout.list_amplop, amplopList, { itemView, item ->
                        itemView.tv_rincian.text = item.noamplop
                        itemView.detail_rincian_penerima.text = item.penerima
                        itemView.detail_rincian_pengirim.text = item.pengirim
                        itemView.detail_rincian_berat.text = item.berat
                        itemView.detail_rincian_jenis.text = item.jenisamplop
                    }, { _, item ->
                        val intent = Intent(applicationContext, DetailOrderActivity::class.java)
//
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

        val noamplop = mDialogView.findViewById<TextView>(R.id.tv_rincian)

        FirebaseDatabase.getInstance().reference
            .child("Amplop")
            .orderByChild("noamplop").equalTo(ed_searchAmplop.text.toString().trim())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.children.first().value as Map<String, Int>
                    noamplop.text = map["noamplop"].toString()
                }
            })
        mDialogView.bt_cari_data.setOnClickListener {
            mAlertDialog.dismiss()
    }
        }
}