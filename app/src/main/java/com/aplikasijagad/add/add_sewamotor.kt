package com.aplikasijagad.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplikasijagad.DashboardSewa
import com.aplikasijagad.R
import kotlinx.android.synthetic.main.add_sewamobil.*

class add_sewamotor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_sewamotor)
//        home.setOnClickListener { startActivity(Intent(this, DashboardSewa::class.java)) }
    }
}