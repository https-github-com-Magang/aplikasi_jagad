package com.aplikasijagad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.aplikasijagad.R

//class KurirAdapter(val mCtx: Context, val layoutResId: Int, val list: List<Loket> )
//    : ArrayAdapter<Loket>(mCtx,layoutResId,list) {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
//        val view: View = layoutInflater.inflate(layoutResId, null)
//
//        val textNama = view.findViewById<TextView>(R.id.ed_nmPenerima)
//        val noId = view.findViewById<TextView>(R.id.ed_beratBarang)
//
//        val user = list[position]
//
//        textNama.text = user.nama
//        noId.text = user.status
//
//        return view
//
//    }
//}