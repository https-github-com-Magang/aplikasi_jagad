package com.aplikasijagad

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.aplikasijagad.models.Amplop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.accepted.view.*
import kotlinx.android.synthetic.main.activity_detail_amplop.*
import kotlinx.android.synthetic.main.rejected.view.*

class DetailAmplopActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var listAmplop: MutableList<Amplop>
    private lateinit var database: FirebaseDatabase
    private lateinit var adapter: AdapterUtil<Amplop>
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_amplop)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        listAmplop = mutableListOf()
        user = auth.currentUser!!

        val data = intent.getParcelableExtra<Amplop>("data")
        detail_amplop_berat.text = data?.berat
        detail_amplop_jenis.text = data?.jenisamplop
        detail_amplop_penerima.text = data?.penerima
        detail_amplop_pengirim.text = data?.pengirim
        detail_amplop_status.text = data?.status

        if (data?.status == "Return" || data?.status == "Diterima") {
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
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.accepted, null)
            builder.setView(view)
            val dialog = builder.show()
            val penerima = view.findViewById<EditText>(R.id.penerima).text

            view.save_builder.setOnClickListener {
                amplop.child("diterima").setValue(penerima.toString())
                amplop.child("status").setValue("Diterima")
                dialog.dismiss()
                btn_terima.visibility = View.INVISIBLE
                btn_tolak.visibility = View.INVISIBLE
            }

            view.close_builder.setOnClickListener {
                dialog.dismiss()
            }
        }

        btn_tolak.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.rejected, null)
            builder.setView(view)
            val dialog = builder.show()
            val ditolak = view.findViewById<EditText>(R.id.penolak).text

            view.save_builders.setOnClickListener {
                amplop.child("return").setValue(ditolak.toString())
                amplop.child("status").setValue("Return")
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