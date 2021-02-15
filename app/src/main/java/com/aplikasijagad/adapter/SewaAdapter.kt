package com.aplikasijagad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasijagad.R
import com.aplikasijagad.database.SewaKendaraan

import kotlinx.android.synthetic.main.list_sewa.view.*

class SewaAdapter(private val sewaList: List<SewaKendaraan>) : RecyclerView.Adapter<SewaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_sewa,parent,false)

        return ViewHolder(itemView)
    }


    override fun getItemCount() = sewaList.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tv_namaP: TextView = itemView.findViewById(R.id.tv_namaP)
        val tv_noKTppem: TextView = itemView.findViewById(R.id.tv_noKtppem)
    }

    override fun onBindViewHolder(holder: SewaAdapter.ViewHolder, position: Int) {
        val currentItem = sewaList[position]
        holder.itemView.tv_namaP.text = currentItem.nama_pengirim_sewa
        holder.itemView.tv_noKtppem.text = currentItem.no_Ktp_sewa
    }

}


//class SewaAdapter : RecyclerView.Adapter<SewaAdapter.MainHolder>() {
//    inner class MainHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//        private lateinit var sewa :SewaKendaraan
//        fun  bind(sewa: SewaKendaraan){
//            view.tv_namaP.text=sewa.nama_pengirim_sewa
//            view.tv_noKtppem.text=sewa.no_Ktp_sewa
//
//        }
//    }
//    private val selectionIds = ArrayList<Int>()
//    fun toggleSelection(pos: Int) {
//        val id = getItem(pos).id
//        if (selectionIds.contains(id))
//            selectionIds.remove(id)
//        else
//            selectionIds.add(id)
//        notifyDataSetChanged()
//    }
//    fun getSelection(): List<Int> {
//        return selectionIds
//    }
//    fun resetSelection() {
//        selectionIds.clear()
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_sewa,parent,false)
//        return MainHolder(view)
//    }
//
//
//
//    override fun onBindViewHolder(holder: MainHolder, position: Int) {
//        TODO("Not yet implemented")
//        holder.bind(getItem(position))
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//
//
//}