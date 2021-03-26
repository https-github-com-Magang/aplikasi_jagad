package com.aplikasijagad.kurir

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplikasijagad.AdapterUtil
import com.aplikasijagad.DetailOrderActivity
import com.aplikasijagad.MainActivity
import com.aplikasijagad.R
import com.aplikasijagad.adapter.SuratJalanAdapter
import com.aplikasijagad.database.Order
import com.aplikasijagad.models.Users
import com.aplikasijagad.databinding.FragmentHomeKurirBinding
import com.aplikasijagad.models.SURATJALAN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home_kurir.tv_totkurir
import kotlinx.android.synthetic.main.list_laporan_kurir.view.*
import kotlinx.android.synthetic.main.list_suratjalan.view.*

class HomeKurirFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var listUsers: MutableList<Users>
    private lateinit var listSuratjalan: MutableList<SURATJALAN>
    private lateinit var adapter: AdapterUtil<SURATJALAN>
    private lateinit var user: FirebaseUser
    private lateinit var binding: FragmentHomeKurirBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        listUsers = mutableListOf()
        listSuratjalan = mutableListOf()
        user = auth.currentUser!!
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_kurir, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoProfile()
        orderKurir()

    }

    private fun infoProfile() {
        val uid = user.uid

        database.getReference("Users").orderByChild("uid").equalTo(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(
                        requireContext(),
                        "Error",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (userSnapshot in p0.children) {
                            val data = userSnapshot.getValue(Users::class.java)
                            data?.let { listUsers.add(it) }
                            tv_totkurir.text = data!!.name
                        }
                    }
                }
            })
    }

    private fun orderKurir() {
        val uid = user.uid

        database.getReference("SURATJALAN").orderByChild("uidDriver").equalTo(uid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (eventSnapshot in p0.children) {
                        val data = eventSnapshot.getValue(SURATJALAN::class.java)
                        data?.let {
                            listSuratjalan.add(it)
                        }
                    }
                }

                adapter = AdapterUtil(R.layout.list_suratjalan, listSuratjalan, { itemView, item ->
                    itemView.tv_noSTB.text = item.uidSRJ
                    itemView.tv_tglsurat.text = item.tanggal
                    itemView.tv_Tujuan.text = item.tujuan
                    itemView.tv_drive.text = item.driver
                }, { _, item ->
                    val intent = Intent(requireContext(), DetailOrderActivity::class.java)
                    intent.putExtra("data", item)
                    startActivity(intent)
                })

                binding.rvLaporanKurir.apply {
                    this.adapter = this@HomeKurirFragment.adapter
                    this.layoutManager = LinearLayoutManager(context)
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeKurirFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
