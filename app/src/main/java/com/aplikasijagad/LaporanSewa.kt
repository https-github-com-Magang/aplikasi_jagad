package com.aplikasijagad

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasijagad.R.id.rv_laporansewa
import com.aplikasijagad.adapter.SewaAdapter
import com.aplikasijagad.add.add_sewa_kendaraan
import com.aplikasijagad.database.Order
import com.aplikasijagad.database.SewaKendaraan
import com.google.firebase.database.*

class LaporanSewa() : AppCompatActivity() {
    lateinit var ref: DatabaseReference
    private lateinit var sewaList: MutableList<SewaKendaraan>
//    private lateinit var adapter: SewaAdapter<SewaKendaraan>
    private lateinit var listsewa: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_sewa)
        ref = FirebaseDatabase.getInstance().getReference("SEWA")
        sewaList = mutableListOf()

        listsewa = findViewById(rv_laporansewa)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
//                  sewaList.clear()
                    for (h in p0.children) {
                        val sewa = h.getValue(SewaKendaraan::class.java)
                        if (sewa != null) {
                            sewaList.add(sewa)
                        }
                    }
                    val adapter = SewaAdapter(applicationContext, R.layout.list_sewa, sewaList)
                    listsewa.adapter = adapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}

