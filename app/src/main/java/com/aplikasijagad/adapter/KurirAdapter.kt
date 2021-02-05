package com.aplikasijagad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasijagad.R
import com.aplikasijagad.database.Loket
import com.aplikasijagad.model.KurirViewHolder
import java.util.ArrayList

class KurirAdapter(private val context: Context,
                   private val daftarLoket: ArrayList<Loket?>?) : RecyclerView.Adapter<KurirViewHolder>() {

    private val listener: FirebaseDataListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KurirViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view_deliveryorder, parent, false)
        return KurirViewHolder(view)
    }

    override fun onBindViewHolder(holder: KurirViewHolder, position: Int) {
        //val generator = ColorGenerator.MATERIAL //custom color
        //val color = generator.randomColor

        //holder.view.setCardBackgroundColor(color)
        holder.idPengirim.text = (daftarLoket?.get(position)?.berat_barang)
        holder.namaPengirim.text = (daftarLoket?.get(position)?.nama)
        holder.view.setOnClickListener{ listener.onDataClick(daftarLoket?.get(position), position)}
    }

    override fun getItemCount(): Int {
        return daftarLoket?.size!!
    }

    interface FirebaseDataListener {
        fun onDataClick(barang: Loket?, position: Int)
    }

    init {
        listener = context as FirebaseDataListener
    }
}