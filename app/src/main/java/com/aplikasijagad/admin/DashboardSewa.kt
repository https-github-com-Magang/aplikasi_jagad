package com.aplikasijagad.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplikasijagad.R
import kotlinx.android.synthetic.main.activity_dashboard_sewa.*

class DashboardSewa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home_sewa)

        btn_motor.setOnClickListener { launchIntent(HomeSewaFragment::class.java) }
        btn_mobil.setOnClickListener { launchIntent(HomeSewaFragment::class.java) }
    }

    @SuppressLint("PrivateResource")
    private fun launchIntent(activity: Class<*>){
        val intent = Intent(applicationContext, activity)
        startActivity(intent)
        overridePendingTransition(
            R.anim.abc_fade_in,
            R.anim.abc_fade_out
        )
    }
}