package com.aplikasijagad.admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aplikasijagad.MainActivity
import com.aplikasijagad.R
import com.aplikasijagad.models.Users
import com.aplikasijagad.databinding.FragmentProfileAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile_admin.*

class ProfileAdminFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var listUsers: MutableList<Users>
    private lateinit var binding: FragmentProfileAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        listUsers = mutableListOf()

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_admin, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        load()

        logoutAdmin.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
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
                    )
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (userSnapshot in p0.children) {
                            val data = userSnapshot.getValue(Users::class.java)
                            data?.let { listUsers.add(it) }
                            tv_namaAdmin.text = data!!.name
                            tv_nikAdmin.text = data.nik
                            tv_nohpAdmin.text = data.phone
                            tv_emailAdmin.text = data.email
                            tv_Admin.text = data.usertype
                            tv_alamatAdmin.text = data.address
                        }
                    }
                }
            })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileAdminFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}