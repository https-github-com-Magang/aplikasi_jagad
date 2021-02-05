package com.aplikasijagad.model

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasijagad.R
import kotlinx.android.synthetic.main.activity_add_loket.view.*

class KurirViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    @JvmField
    var idPengirim: TextView

    @JvmField
    var namaPengirim: TextView

    @JvmField
    var view: CardView

    init {
        idPengirim = itemView.findViewById(R.id.ed_beratBarang)
        namaPengirim = itemView.findViewById(R.id.ed_nmPenerima)
        view = itemView.findViewById(R.id.cv_list_view)
    }
}