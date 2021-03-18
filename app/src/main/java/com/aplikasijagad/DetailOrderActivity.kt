package com.aplikasijagad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplikasijagad.models.Amplop
import com.aplikasijagad.models.SURATJALAN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_detail_order.*
import kotlinx.android.synthetic.main.list_amplop.view.*

class DetailOrderActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var listOrder: MutableList<SURATJALAN>
    private lateinit var listAmplop: MutableList<Amplop>
    private lateinit var database: FirebaseDatabase
    private lateinit var adapter: AdapterUtil<Amplop>
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        listOrder = mutableListOf()
        listAmplop = mutableListOf()
        user = auth.currentUser!!

        val data = intent.getParcelableExtra<SURATJALAN>("data")
        detail_kode.text = data?.uidSRJ
        detail_driver.text = data?.driver
        detail_tanggal.text = data?.tanggal
        detail_tujuan.text = data?.tujuan

        database.getReference("SURATJALAN").child(data!!.uidSRJ).child("Amplop").orderByChild("idSRJ").equalTo(data.uidSRJ).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (eventSnapshot in p0.children) {
                        val data = eventSnapshot.getValue(Amplop::class.java)
                        data?.let {
                            listAmplop.add(it)
                        }
                    }
                }

                adapter = AdapterUtil(R.layout.list_amplop, listAmplop, { itemView, item ->
                    itemView.tv_rincian.text = item.noamplop
                    itemView.detail_rincian_penerima.text = item.penerima
                    itemView.detail_rincian_pengirim.text = item.pengirim
                    itemView.detail_rincian_berat.text = item.berat
                    itemView.detail_rincian_jenis.text = item.jenisamplop
                }, { _, item ->
                    val intent = Intent(applicationContext, DetailOrderActivity::class.java)
//        
                })

                rvLaporanAmplop.apply {
                    adapter = this@DetailOrderActivity.adapter
                    layoutManager = LinearLayoutManager(context)
                }
            }
        })
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