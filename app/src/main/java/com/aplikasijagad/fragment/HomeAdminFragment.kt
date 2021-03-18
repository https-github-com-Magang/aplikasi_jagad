package com.aplikasijagad.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aplikasijagad.LaporanSewa
import com.aplikasijagad.MapsActivity
import com.aplikasijagad.R
import com.aplikasijagad.add.add_order
import com.aplikasijagad.add.add_sewa_kendaraan
import com.aplikasijagad.databinding.FragmentHomeAdminBinding
import kotlinx.android.synthetic.main.fragment_home_admin.*

class HomeAdminFragment : Fragment() {

    private lateinit var binding: FragmentHomeAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home_admin, container, false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_loket.setOnClickListener {
            val intents = Intent(requireContext(), add_order::class.java)
            startActivity(intents)
        }

        btn_sewa.setOnClickListener {
            val intents = Intent(requireContext(),add_sewa_kendaraan::class.java)
            startActivity(intents)
        }

        btn_posisikurir.setOnClickListener {
            val intents = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intents)
        }

        btn_laporan.setOnClickListener {
            val intents = Intent(requireContext(), LaporanSewa::class.java)
            startActivity(intents)
        }
    }
}