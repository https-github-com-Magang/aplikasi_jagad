package com.aplikasijagad.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aplikasijagad.Model.APICoronaProv
import com.aplikasijagad.Model.ApiSPB
import com.aplikasijagad.Model.DataAPI

//import com.aplikasijagad.Model.ResiBarang
import com.aplikasijagad.R
import com.aplikasijagad.databinding.ListAmplopBinding
import kotlinx.android.synthetic.main.list_amplop.view.*

class ResiAdapter :
    RecyclerView.Adapter<ResiAdapter.ResiAdapterViewHolder>() {
    inner class ResiAdapterViewHolder(
        val recyclerciewListItemResiBinding
        : ListAmplopBinding
    ) :
        RecyclerView.ViewHolder(recyclerciewListItemResiBinding.root)

    private var myList = emptyList<DataAPI>()

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int) = ResiAdapterViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context) ,
            R.layout.list_amplop ,
            parent ,
            false
        )
    )


    override fun getItemCount(): Int = myList.size

    override fun onBindViewHolder(holder: ResiAdapterViewHolder , position: Int) {
        holder.recyclerciewListItemResiBinding.tvRincian1.text = myList[position].id_surat_jalan
        holder.recyclerciewListItemResiBinding.detailRincianPenerima.text = myList[position].hp_driver
        holder.recyclerciewListItemResiBinding.detailRincianPengirim.text = myList[position].nama_pengirim
        holder.recyclerciewListItemResiBinding.detailRincianBerat.text = myList[position].alamat_pengirim
        holder.recyclerciewListItemResiBinding.detailRincianJenis.text = myList[position].tanggal
    }

    fun setData(newList: List<DataAPI>) {
        myList = newList
        notifyDataSetChanged()
    }


}