package com.aplikasijagad.kurir

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplikasijagad.AdapterUtil
import com.aplikasijagad.DetailAmplopActivity
import com.aplikasijagad.DetailOrderActivity
import com.aplikasijagad.R
import com.aplikasijagad.databinding.FragmentHistoryBinding
import com.aplikasijagad.databinding.FragmentHomeKurirBinding
import com.aplikasijagad.models.Amplop
import com.aplikasijagad.models.SURATJALAN
import com.aplikasijagad.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_detail_order.*
import kotlinx.android.synthetic.main.list_amplop.view.*
import kotlinx.android.synthetic.main.list_suratjalan.view.*

class HistoryFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var listOrder: MutableList<SURATJALAN>
    private lateinit var listAmplop: MutableList<Amplop>
    private lateinit var adapter: AdapterUtil<Amplop>
    private lateinit var user: FirebaseUser
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        listAmplop = mutableListOf()
        user = auth.currentUser!!

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyAmplop()


    }
    private fun historyAmplop() {
        val uid = user.uid

        database.getReference("SURATJALAN").child("Amplop").equalTo(uid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (eventSnapshot in p0.children) {
                        val data = eventSnapshot.getValue(Amplop::class.java)
                        data?.let {
                            listAmplop.add(it)
                        }
                    }
                }

                adapter = AdapterUtil(R.layout.list_amplop, listAmplop, { itemView, item ->
                    itemView.tv_rincian1.text = item.noamplop
                    itemView.detail_rincian_penerima.text = item.penerima
                    itemView.detail_rincian_pengirim.text = item.pengirim
                    itemView.detail_rincian_berat.text = item.berat
                    itemView.detail_rincian_jenis.text = item.jenisamplop
                    itemView.detail_rincian_status.text = item.status
                }, { _, item ->
//                    val intent = Intent(requireContext(), DetailOrderActivity::class.java)
//                    intent.putExtra("data", item)
//                    startActivity(intent)
                })

                binding.rvLaporanAmplop.apply {
                    this.adapter = this@HistoryFragment.adapter
                    this.layoutManager = LinearLayoutManager(context)
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}