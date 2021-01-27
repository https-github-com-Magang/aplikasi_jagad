package com.aplikasijagad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.aplikasijagad.add.add_loket
import kotlinx.android.synthetic.main.activity_dashboard_admin.*

//import kotlinx.android.synthetic.main.activity_dashboard_kurir.*
//import kotlinx.android.synthetic.main.activity_dashboard_utama_admin.*
//import kotlinx.android.synthetic.main.activity_dashboard_utama_admin.btn_sewa
//import kotlinx.android.synthetic.main.activity_dashboard_utama_kurir.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)

////        btn_kurir.setOnClickListener { startActivity(Intent(this, LoginKurir::class.java)) }
////        btn_admin.setOnClickListener { startActivity(Intent(this, LoginAdmin::class.java)) }
        btn_sewa.setOnClickListener { startActivity(Intent(this, DashboardSewa::class.java)) }
        btn_loket.setOnClickListener { startActivity(Intent(this, add_loket::class.java)) }
    }


}