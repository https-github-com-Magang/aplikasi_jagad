package com.aplikasijagad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var usertype: String
    private lateinit var listUsers: MutableList<Users>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        listUsers = mutableListOf()

        btn_logadmin.setOnClickListener {
            usertype = "Admin"
            btn_logadmin.setBackgroundColor(resources.getColor(R.color.gray))
            btn_logcourier.setBackgroundColor(resources.getColor(R.color.white))
        }

        btn_logcourier.setOnClickListener {
            usertype = "Courier"
            btn_logcourier.setBackgroundColor(resources.getColor(R.color.gray))
            btn_logadmin.setBackgroundColor(resources.getColor(R.color.white))
        }

        btn_login.setOnClickListener {
            logIn()

        }
    }

    private fun logIn() {
        if (checkInput()) {

            val nik = et2_lognik.text.toString().trim()
            val password = et2_logpassword.text.toString().trim()

            database.orderByChild("nik").equalTo(nik)
                .addValueEventListener(object : ValueEventListener {

                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.exists()) {
                            for (userSnapshot in p0.children) {
                                val pass = userSnapshot.getValue(Users::class.java)
                                pass?.let { listUsers.add(it) }
                                if (pass!!.password == password) {

                                    loginUser(pass.email, password)

                                    if (usertype == "Admin") {
                                        startActivity(
                                            Intent(
                                                this@LoginActivity,
                                                DashboardAdmin::class.java
                                            )
                                        )
                                    }

                                    if (usertype == "Courier") {
                                        startActivity(
                                            Intent(
                                                this@LoginActivity,
                                                DashboardKurir::class.java
                                            )
                                        )
                                    } else {
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "Wrong Password",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Record Not Found",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            Toast.makeText(this, "Select User", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkInput(): Boolean {
        if (et2_lognik.text.isNullOrBlank() || et2_logpassword.text.isNullOrBlank() || usertype.isNullOrEmpty()) {
            Toast.makeText(this@LoginActivity, "Fields cannot be null", Toast.LENGTH_SHORT).show()
            return false
        } else
            return true
    }

    private fun loginUser(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
    }
}
