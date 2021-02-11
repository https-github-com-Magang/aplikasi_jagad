package com.aplikasijagad

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aplikasijagad.fragment.HomeKurirFragment
import com.aplikasijagad.fragment.ProfileKurirFragment
import com.aplikasijagad.fragment.ViewDeliveryOrderFragment
import kotlinx.android.synthetic.main.activity_dashboard_kurir.*
import kotlinx.android.synthetic.main.fragment_home_kurir.*

class DashboardKurir : AppCompatActivity() {
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_kurir)

        val homeKurirFragment = HomeKurirFragment()
        val viewDOFragment = ViewDeliveryOrderFragment()
        val profileKurirFragment = ProfileKurirFragment()

        makeCurrentFragment(homeKurirFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeKurirFragment)
                R.id.ic_view -> makeCurrentFragment(viewDOFragment)
                R.id.ic_akun -> makeCurrentFragment(profileKurirFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}