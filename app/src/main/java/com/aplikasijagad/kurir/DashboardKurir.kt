package com.aplikasijagad.kurir

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aplikasijagad.R
import com.aplikasijagad.kurir.AmplopFragment.Companion.newInstance
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.activity_dashboard_kurir.bottomNavigation
import kotlinx.android.synthetic.main.fragment_home_kurir.*
import javax.xml.datatype.DatatypeFactory.newInstance

class DashboardKurir : AppCompatActivity() {
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_kurir)

        addFragment(HomeKurirFragment.newInstance())
        bottomNavigation.show(0)
        bottomNavigation.add(MeowBottomNavigation.Model(0,R.drawable.homeicon))
        //bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.list))
        bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.akun))

        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 -> {
                    replaceFragment(HomeKurirFragment.newInstance())
                }
//                1 -> {
//                    replaceFragment(HistoryFragment.newInstance())
//                }
                1 -> {
                    replaceFragment(ProfileKurirFragment.newInstance())
                }
                else -> {
                    replaceFragment(HomeKurirFragment.newInstance())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }

    private fun addFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }
}