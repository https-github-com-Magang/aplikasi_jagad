package com.aplikasijagad.kurir

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplikasijagad.API.Repository
import com.aplikasijagad.AdapterUtil
import com.aplikasijagad.DetailOrderActivity
import com.aplikasijagad.R
import com.aplikasijagad.ViewModel.MainViewModel
import com.aplikasijagad.ViewModel.MainViewModelFactory
import com.aplikasijagad.adapter.ResiAdapter
import com.aplikasijagad.auth.LoginActivity
import com.aplikasijagad.databinding.FragmentHomeKurirBinding
import com.aplikasijagad.models.SURATJALAN
import com.aplikasijagad.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home_kurir.*
import kotlinx.android.synthetic.main.list_amplop.view.*


class HomeKurirFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy {
        ResiAdapter()
    }
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
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        listUsers = mutableListOf()
        listSuratjalan = mutableListOf()
        user = auth.currentUser!!
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_kurir, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //reclerview show
        viewModel.getResiviewModel()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    myAdapter.setData(it)
                }
            } else {
            }
        })

        setupRecylerview()
        infoProfile()
        //orderKurir()

    }
    private fun setupRecylerview() {
        val recylerView = binding.rvLaporanKurir
        recylerView.adapter = myAdapter
        recylerView.layoutManager = LinearLayoutManager(requireContext())


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
        database.getReference("SURATJALAN").child("SRJ001").child("Amplop").orderByChild("idSRJ")
            .addListenerForSingleValueEvent(object :ValueEventListener{
//        database.getReference("SURATJALAN").child("SRJ003").child("Amplop")
//            .addListenerForSingleValueEvent(object :ValueEventListener{

//        database.getReference("SURATJALAN")
//            .orderByChild("uidDriver")
//            .equalTo(uid).addListenerForSingleValueEvent(object :
//            ValueEventListener {
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

                adapter = AdapterUtil(R.layout.list_amplop, listSuratjalan, { itemView, item ->
                    itemView.tv_Rincian1.text = item.noamplop
                    itemView.detail_rincian_penerima.text = item.penerima
                    itemView.detail_rincian_pengirim.text = item.pengirim
                    itemView.detail_rincian_berat.text = item.berat
                    itemView.detail_rincian_jenis.text = item.jenisamplop
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
