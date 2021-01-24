//package com.aplikasijagad.Login
//
//import android.app.AlertDialog
//import android.os.Bundle
//import android.util.Patterns
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.EditText
//import android.widget.Toast
//import com.aplikasijagad.MainActivity
//import com.aplikasijagad.R
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.android.synthetic.main.fragment_login_kurir.*
//
//class LoginKurirFragment : Fragment() {
//
//    private lateinit var auth: FirebaseAuth
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login_kurir, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        auth = FirebaseAuth.getInstance()
//
//        btn_logKurir.setOnClickListener {
//            if (checkInput()) {
//                val email = email_edittext.text.toString()
//                val password = password_edittext.text.toString()
//
//                (activity as MainActivity).loginUser(email, password)
//            }
//        }
//
//        ic_close_imageview.setOnClickListener {
//            parentFragment?.childFragmentManager
//                ?.popBackStack()
//        }
//
//        forgot_password.setOnClickListener {
//            openForgotDialog()
//        }
//    }
//
//    private fun openForgotDialog() {
//        val builder = AlertDialog.Builder(requireContext())
//        val view = layoutInflater.inflate(R.layout.forgot_password, null)
//        val email = view.findViewById<EditText>(R.id.inputEmail)
//        builder.setView(view)
//        val dialog = builder.show()
//
//        view.button_reset.setOnClickListener {
//            dialog.dismiss()
//            forgotPassword(email)
//        }
//
//        view.close_builder.setOnClickListener {
//            dialog.dismiss()
//        }
//    }
//
//
//    private fun checkInput(): Boolean {
//        if (email_edittext.text.isNullOrBlank() || password_edittext.text.isNullOrBlank()) {
//            Toast.makeText(context, "Fields cannot be null", Toast.LENGTH_SHORT).show()
//            return false
//        } else
//            return true
//    }
//
//    private fun forgotPassword(email: EditText) {
//        if (email.text.toString().isEmpty()) {
//            return
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
//            return
//        }
//
//        auth.sendPasswordResetEmail(email.text.toString())
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(requireContext(), "Email sent.", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//}