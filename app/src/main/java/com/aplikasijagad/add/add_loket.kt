package com.aplikasijagad.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplikasijagad.DashboardKurir
import com.aplikasijagad.R
import kotlinx.android.synthetic.main.activity_add_loket.*

class add_loket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_loket)
        home.setOnClickListener { startActivity(Intent(this, DashboardKurir::class.java)) }
    }
}