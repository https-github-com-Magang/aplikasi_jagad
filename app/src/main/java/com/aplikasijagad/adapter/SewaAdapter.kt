package com.aplikasijagad.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasijagad.R
import com.aplikasijagad.database.SewaKendaraan
import android.content.Context

import kotlinx.android.synthetic.main.list_sewa.view.*

class SewaAdapter (val mCtx : Context, val layoutResId:Int, val sewaList: List<SewaKendaraan>)
    : ArrayAdapter<SewaKendaraan>(mCtx, layoutResId, sewaList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(layoutResId, null)
        val tvNama : TextView = view .findViewById(R.id.tv_namaP)
        val tvKTP : TextView = view.findViewById(R.id.tv_noKtppem)
        val jmlhHari : TextView= view.findViewById(R.id.tv_jmlhHari)
        val jenis : TextView = view.findViewById(R.id.tv_jenis)
        val sewa = sewaList[position]

        tvNama.text = sewa.nama_pengirim_sewa
        tvKTP.text=sewa.no_Ktp_sewa
        jmlhHari.text= sewa.hari_sewa
        jenis.text= sewa.kendaraan_sewa
        return view

    }


}