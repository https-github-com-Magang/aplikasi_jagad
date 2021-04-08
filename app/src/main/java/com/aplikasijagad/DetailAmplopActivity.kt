package com.aplikasijagad

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.aplikasijagad.auth.LoginActivity
import com.aplikasijagad.models.Amplop
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.accepted.view.*
import kotlinx.android.synthetic.main.activity_detail_amplop.*
import kotlinx.android.synthetic.main.activity_diterima.*
import kotlinx.android.synthetic.main.rejected.view.*
import java.util.HashMap

class DetailAmplopActivity : AppCompatActivity() {

    private lateinit var uri: Uri
    private lateinit var auth: FirebaseAuth
    private lateinit var listAmplop: MutableList<Amplop>
    private lateinit var database: FirebaseDatabase
    private lateinit var user: FirebaseUser
    private lateinit var dropDownText: AutoCompleteTextView

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    private var storageRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_amplop)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storageRef = FirebaseStorage.getInstance().reference.child("Bukti images")
        listAmplop = mutableListOf()
        user = auth.currentUser!!

        val data = intent.getParcelableExtra<Amplop>("data")
        detail_amplop_berat.text = data?.berat
        detail_amplop_jenis.text = data?.jenisamplop
        detail_amplop_penerima.text = data?.penerima
        detail_amplop_pengirim.text = data?.pengirim
        detail_amplop_status.text = data?.status

        if (data?.status == "Ditolak" || data?.status == "Diterima") {
            btn_terima.visibility = View.INVISIBLE
            btn_tolak.visibility = View.INVISIBLE
        } else {
            btn_terima.visibility = View.VISIBLE
            btn_tolak.visibility = View.VISIBLE
        }

        val idSRJ = data?.idSRJ.toString()
        val idAMP = data?.noamplop.toString()
        val amplop = database.getReference("SURATJALAN").child(idSRJ).child("Amplop")
            .child(idAMP)


        btn_terima.setOnClickListener {

            startActivity(Intent(this, DiterimaActivity::class.java))

//            val builder = AlertDialog.Builder(this)
//            val view = layoutInflater.inflate(R.layout.accepted , null)
//            builder.setView(view)
//            val dialog = builder.show()
//            val penerima = view.findViewById<EditText>(R.id.penerima).text
//
//            view.save_data.setOnClickListener {
//                amplop.child("diterima").setValue(penerima.toString())
//                amplop.child("status").setValue("Diterima")
//                dialog.dismiss()
//                btn_terima.visibility = View.INVISIBLE
//                btn_tolak.visibility = View.INVISIBLE
//            }
//
//            view.close_builder.setOnClickListener {
//                dialog.dismiss()
//            }
        }

        btn_tolak.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.rejected , null)
            builder.setView(view)
            val dialog = builder.show()
            val ditolak = view.findViewById<EditText>(R.id.penolak).text

            view.save_builders.setOnClickListener {
                amplop.child("ditolak").setValue(ditolak.toString())
                amplop.child("status").setValue("Ditolak")
                dialog.dismiss()
                btn_terima.visibility = View.INVISIBLE
                btn_tolak.visibility = View.INVISIBLE
            }

            view.close_builders.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

}