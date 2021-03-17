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
//class SewaAdapter <T>(var layout: Int,
//                    private var items: List<T>,
//                    var  view:(View, T)-> Unit,
//                    var handler:(Int, T)-> Unit): RecyclerView.Adapter<SewaAdapter.ViewHolder<T>>() {
//    var data = this.items
//        set(value) {
//            field= value
//            notifyDataSetChanged()
//        }
//    class ViewHolder<T>(itemView: View):RecyclerView.ViewHolder(itemView) {
//        fun bind(item: T, view:(View, T)-> Unit){
//            view(itemView, item)
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
//       return ViewHolder(LayoutInflater.from(parent.context).inflate(this.layout,parent, false))
//    }
//
//
//    override fun getItemCount(): Int {
//       return this.data.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
//       val item: T = this.data[position]
//        holder.bind(item, view)
//        holder.itemView.setOnClickListener{handler(position,item)}
//    }
//}
class SewaAdapter (val mCtx : Context, val layoutResId:Int, val sewaList: List<SewaKendaraan>)
    : ArrayAdapter<SewaKendaraan>(mCtx, layoutResId, sewaList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(layoutResId, null)
        val tvNama : TextView = view .findViewById(R.id.tv_namaP)
        val tvKTP : TextView = view.findViewById(R.id.tv_noKtppem)
        val jmlhHari : TextView= view.findViewById(R.id.tv_jmlHari)
        val jenis : TextView = view.findViewById(R.id.tv_jenis)
        val sewa = sewaList[position]

        tvNama.text = sewa.nama_pengirim_sewa
        tvKTP.text=sewa.no_Ktp_sewa
        jmlhHari.text= sewa.hari_sewa
        jenis.text= sewa.kendaraan_sewa
        return view

    }


}
