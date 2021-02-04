package com.aplikasijagad

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aplikasijagad.fragment.*
import kotlinx.android.synthetic.main.activity_dashboard_admin.*

class DashboardAdmin : AppCompatActivity() {
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)

//        btn_update.setOnClickListener { launchIntent(UbahStatusDeliv::class.java) }
//        btn_delivorder.setOnClickListener { launchIntent(DeliveryOrder::class.java) }

        val homeAdminFragment = HomeAdminFragment()
        val profileAdminFragment = ProfileAdminFragment()

        makeCurrentFragment(homeAdminFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeAdminFragment)
                R.id.ic_akun -> makeCurrentFragment(profileAdminFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapperr, fragment)
            commit()
        }

//    @SuppressLint("PrivateResource")
//    private fun launchIntent(activity: Class<*>){
//        val intent = Intent(applicationContext, activity)
//        startActivity(intent)
//        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
//    }



}