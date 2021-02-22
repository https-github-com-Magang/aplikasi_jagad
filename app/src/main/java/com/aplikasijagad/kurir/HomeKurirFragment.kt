package com.aplikasijagad.kurir

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplikasijagad.AdapterUtil
import com.aplikasijagad.DetailOrderActivity
import com.aplikasijagad.R
import com.aplikasijagad.database.Order
import com.aplikasijagad.models.Users
import com.aplikasijagad.databinding.FragmentHomeKurirBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home_kurir.*
import kotlinx.android.synthetic.main.list_laporan_kurir.view.*

class HomeKurirFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var listUsers: MutableList<Users>
    private lateinit var listOrders: MutableList<Order>
    private lateinit var adapter: AdapterUtil<Order>
    private lateinit var user: FirebaseUser
    private lateinit var binding: FragmentHomeKurirBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        listUsers = mutableListOf()
        listOrders = mutableListOf()
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
                            tv_totloket.text = data.nik
                        }
                    }
                }
            })
    }

    private fun orderKurir() {
        val uid = auth.currentUser!!.uid

        database.getReference("ORDER").orderByChild("kurirId").equalTo(uid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (eventSnapshot in p0.children) {
                        val data = eventSnapshot.getValue(Order::class.java)
                        data?.let {
                            listOrders.add(it)
                        }
                    }
                }

                adapter = AdapterUtil(R.layout.list_laporan_kurir, listOrders, { itemView, item ->
                    itemView.tv_totalloket.text = item.orderId
                    itemView.tv_pengirim.text = item.namaPenerima
                    itemView.tv_alamatPenerima.text = item.alamat
                }, { _, item ->
                    val intent = Intent(requireContext(), DetailOrderActivity::class.java)
                    intent.putExtra("data", item)
                    startActivity(intent)
                })

                binding.rvOrderKurir.apply {
                    this.adapter = this@HomeKurirFragment.adapter
                    this.layoutManager = LinearLayoutManager(context)
                }
            }
        })
    }
}

