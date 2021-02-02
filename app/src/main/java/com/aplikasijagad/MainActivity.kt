package com.aplikasijagad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aplikasijagad.fragment.HomeKurirFragment
import com.aplikasijagad.fragment.ProfileKurirFragment
import com.aplikasijagad.fragment.ViewDeliveryOrderFragment
import kotlinx.android.synthetic.main.activity_dashboard_kurir.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_admin.setOnClickListener { launchIntent(DashboardAdmin::class.java) }
        btn_kurir.setOnClickListener { launchIntent(DashboardKurir::class.java) }

    }


    @SuppressLint("PrivateResource")
    private fun launchIntent(activity: Class<*>){
        val intent = Intent(applicationContext, activity)
        startActivity(intent)
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
    }

    @SuppressLint("PrivateResource")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
    }
}