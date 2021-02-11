package com.aplikasijagad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var usertype: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        btn_admin.setOnClickListener {
            usertype = "Admin"
            btn_admin.setBackgroundColor(resources.getColor(R.color.gray))
            btn_courier.setBackgroundColor(resources.getColor(R.color.white))
        }

        btn_courier.setOnClickListener {
            usertype = "Courier"
            btn_courier.setBackgroundColor(resources.getColor(R.color.gray))
            btn_admin.setBackgroundColor(resources.getColor(R.color.white))
        }

        btn_signup.setOnClickListener {
            signUp()
        }

        tv_loginacc.setOnClickListener {
            logIn()
        }
    }

    private fun logIn() {
        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
    }

    private fun signUp() {
        val name = et2_name.text.toString().trim()
        val nik = et2_nik.text.toString().trim()
        val email = et2_email.text.toString().trim()
        val password = et2_password.text.toString().trim()
        val phone = et2_phone.text.toString().trim()
        val address = et2_address.text.toString().trim()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val uid = auth.currentUser!!.uid
                    val data = Users(uid, name, nik, email, password, phone, usertype, address)
                    database.child(uid).setValue(data)
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this@SignupActivity,
                                e.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@SignupActivity,
                                "Data has been saved",
                                Toast.LENGTH_LONG
                            ).show()
                            startActivity(Intent(this@SignupActivity, DashboardAdmin::class.java))
                        }
                }
            }
    }
}