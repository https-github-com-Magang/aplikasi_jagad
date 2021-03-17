package com.aplikasijagad.admin
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aplikasijagad.DashboardSewa
import com.aplikasijagad.LaporanSewa
import com.aplikasijagad.MapsActivity
import com.aplikasijagad.R
import com.aplikasijagad.add.add_order
import com.aplikasijagad.add.add_sewa_kendaraan
import com.aplikasijagad.database.Order
import com.aplikasijagad.database.SewaKendaraan
import com.aplikasijagad.databinding.FragmentHomeAdminBinding
import com.aplikasijagad.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_laporan_sewa.*
import kotlinx.android.synthetic.main.fragment_home_admin.*

class HomeAdminFragment : Fragment() {

    private lateinit var binding: FragmentHomeAdminBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var namadriver: DatabaseReference
    private lateinit var databaseAmplop: DatabaseReference
    private lateinit var databaseSuratjalan: DatabaseReference
    private lateinit var listUsers: MutableList<Users>

    private var currentUserId: String = ""
    private var countfriend: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        namadriver = FirebaseDatabase.getInstance().getReference().child("DRIVER")
        databaseAmplop = FirebaseDatabase.getInstance().getReference().child("Amplop")
        databaseSuratjalan = FirebaseDatabase.getInstance().getReference().child("suratjalan")

        listUsers = mutableListOf()

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home_admin, container, false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        load()
        getchildrenscountdriver()
        getchildrenscountloket()
        getchildrenscountsewa()

        btn_sewa.setOnClickListener {
            val intents = Intent(requireContext(), DashboardSewa::class.java)
            startActivity(intents)
        }
        btn_posisikurir.setOnClickListener {
            val intents = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intents)
        }
    }

    private fun load() {
        val uid = auth.currentUser!!.uid
        database.orderByChild("uid").equalTo(uid)
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
                            tv_nama_adm.text = data!!.name
                        }
                    }
                }
            })
    }

    private fun getchildrenscountdriver() {
        namadriver.child(currentUserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // get total available quest
                if (dataSnapshot.exists()) {
                    countfriend = dataSnapshot.childrenCount.toInt()
                    tv_totalkurir.setText(Integer.toString(countfriend) + " kurir")
                } else {
                    tv_totalkurir.setText("0 kurir")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun getchildrenscountloket() {
        databaseAmplop.child(currentUserId).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // get total available quest
                if (dataSnapshot.exists()) {
                    countfriend = dataSnapshot.childrenCount.toInt()
                    tv_totalloket.setText(Integer.toString(countfriend) + " Amplop")
                } else {
                    tv_totalloket.setText("0 Amplop")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun getchildrenscountsewa() {
        databaseSuratjalan.child(currentUserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // get total available quest
                if (dataSnapshot.exists()) {
                    countfriend = dataSnapshot.childrenCount.toInt()
                    tv_totalsewa.setText(Integer.toString(countfriend) + " Surat Jalan")
                } else {
                    tv_totalsewa.setText("0 Surat Jalan")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeAdminFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}