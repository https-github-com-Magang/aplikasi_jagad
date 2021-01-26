package com.aplikasijagad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplikasijagad.Register.RegisterAdmin
import com.aplikasijagad.add.add_sewamobil
import com.aplikasijagad.add.add_sewamotor
import kotlinx.android.synthetic.main.activity_dashboard_sewa.*

class DashboardSewa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_sewa)
        btn_motor.setOnClickListener { startActivity(Intent(this, add_sewamotor::class.java)) }
        btn_mobil.setOnClickListener { startActivity(Intent(this, add_sewamobil::class.java)) }
    }
}