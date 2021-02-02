package com.aplikasijagad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplikasijagad.add.add_loket
import kotlinx.android.synthetic.main.activity_dashboard_admin.*
//import kotlinx.android.synthetic.main.activity_dashboard_kurir.*
//import kotlinx.android.synthetic.main.activity_dashboard_utama_admin.*

class DashboardAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)
        btn_sewa.setOnClickListener { startActivity(Intent(this, DashboardSewa::class.java)) }
        btn_loket.setOnClickListener { startActivity(Intent(this, add_loket::class.java)) }
    }
}