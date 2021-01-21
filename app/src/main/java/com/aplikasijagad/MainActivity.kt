package com.aplikasijagad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.aplikasijagad.Login.LoginAdmin
import com.aplikasijagad.Login.LoginKurir
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_kurir.setOnClickListener { startActivity(Intent(this, LoginKurir::class.java)) }
        btn_admin.setOnClickListener { startActivity(Intent(this, LoginAdmin::class.java)) }
    }
}