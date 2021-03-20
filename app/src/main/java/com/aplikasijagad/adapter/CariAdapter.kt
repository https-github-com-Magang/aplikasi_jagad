package com.aplikasijagad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.aplikasijagad.R
import com.aplikasijagad.models.Amplop
import com.aplikasijagad.models.SURATJALAN

class CariAdapter (val mCtx: Context , val layoutResId: Int , val dataList: List<SURATJALAN>) :
    ArrayAdapter<SURATJALAN>(mCtx, layoutResId, dataList) {

    override fun getView(position: Int , convertView: View? , parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId , null)

        //read data target
        val tv_rincian = view.findViewById<TextView>(R.id.tv_rincian)
        val detail_rincian_penerima = view.findViewById<TextView>(R.id.detail_rincian_penerima)
        val detail_rincian_pengirim = view.findViewById<TextView>(R.id.detail_rincian_pengirim)
        val detail_rincian_jenis = view.findViewById<TextView>(R.id.detail_rincian_jenis)
        val detail_rincian_status = view.findViewById<TextView>(R.id.detail_rincian_status)


        val data = dataList[position]

        tv_rincian.text = data.noamplop

        return view
    }
}

