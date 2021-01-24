package com.aplikasijagad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth
//    private lateinit var database: DatabaseReference
//    private val landingFragment: LandingFragment = LandingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_order_kurir)

//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance().reference
//
//        if (FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser?.isEmailVerified == true)
//            openViewActivity()
//        else if (FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser?.isEmailVerified == false) {
//            Toast.makeText(this, "Please check email for verification", Toast.LENGTH_SHORT).show()
//            openLandingFragment()
//        } else {
//            openLandingFragment()
//        }
    }
}

//    private fun openLandingFragment() {
//        supportFragmentManager
//            .beginTransaction()
//            .setCustomAnimations(
//                R.anim.slide_up_anim,
//                R.anim.slide_down_anim,
//                R.anim.slide_up_anim,
//                R.anim.slide_down_anim
//            )
//            .add(R.id.main_frame_layout, landingFragment)
//            .commit()
//    }
//
//    private fun openViewActivity() {
//        val intent = Intent(this, ViewActivity::class.java)
//        startActivity(intent)
//    }
//
//    fun signUpUser(email: String, password: String, fullname: String) {
//        FirebaseAuth.getInstance()
//            .createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//
//                if (task.isSuccessful) {
//                    val currentUser = auth.currentUser
//                    val userId = currentUser!!.uid
//                    val data = Users(userId, email, fullname)
//                    database.child("Users").child(userId).setValue(data)
//                }
//
//                checkResult(task, true)
//            }
//    }
//
//    fun loginUser(email: String, password: String) {
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(
//            email, password
//        )
//            .addOnCompleteListener { task ->
//                checkResult(task, false)
//            }
//    }
//
//    private fun checkResult(task: Task<AuthResult>, isNew: Boolean) {
//        if (task.isSuccessful) {
//            if (FirebaseAuth.getInstance().currentUser?.isEmailVerified == true) {
//                openViewActivity()
//            } else {
//                if (isNew) {
//                    FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
//                }
//                Toast.makeText(
//                    this,
//                    "Please check email for verification",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        } else {
//            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG)
//                .show()
//        }
//    }
//}