package com.aplikasijagad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aplikasijagad.Login.LoginAdminFragment
import com.aplikasijagad.Login.LoginKurirFragment
import kotlinx.android.synthetic.main.fragment_landing.*


class LandingFragment : Fragment() {

    private val loginKurirFragment: LoginKurirFragment =
        LoginKurirFragment()
    private val loginAdminFragment: LoginAdminFragment =
        LoginAdminFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            btn_kurir.setOnClickListener {
            childFragmentManager
                .beginTransaction()
//                .setCustomAnimations(
//                    R.anim.slide_in_anim,
//                    R.anim.slide_out_anim,
//                    R.anim.slide_in_anim,
//                    R.anim.slide_out_anim)
                .add(R.id.landing_frame, loginKurirFragment)
                .addToBackStack(this.tag)
                .commit()
        }

        btn_admin.setOnClickListener {
            childFragmentManager
                .beginTransaction()
//                .setCustomAnimations(
//                    R.anim.slide_in_anim,
//                    R.anim.slide_out_anim,
//                    R.anim.slide_in_anim,
//                    R.anim.slide_out_anim)
                .add(R.id.landing_frame, loginAdminFragment)
                .addToBackStack(this.tag)
                .commit()
        }
    }
}