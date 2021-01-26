package com.aplikasijagad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplikasijagad.add.add_sewamotor
import kotlinx.android.synthetic.main.activity_dashboard_kurir.*
import kotlinx.android.synthetic.main.activity_dashboard_sewa.*

class DashboardKurir : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_kurir)
        btn_sewa.setOnClickListener { startActivity(Intent(this, DashboardSewa::class.java)) }
    }
}