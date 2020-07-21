package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_riwayat_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang

class RiwayatAdapter (
    private val barangs: ArrayList<Barang>,
    private val clickListener: OnRiwayatItemClickListener
): RecyclerView.Adapter<RiwayatAdapter.DataViewHolder>(){

    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(barang: Barang, action: OnRiwayatItemClickListener){
            itemView.tv_nama_barang.text = barang.namaBarang.capitalize()
            itemView.tv_mount.text = barang.total.toString()
            itemView.tv_count.text = barang.jumlah.toString()
            itemView.setOnClickListener {
                action.onItemClick(barang, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_riwayat_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = barangs.size

    override fun onBindViewHolder(holder: RiwayatAdapter.DataViewHolder, position: Int) =
        holder.bind(barangs[position], clickListener)

    fun addData(list: List<Barang>) {
        barangs.addAll(list)
    }
}

interface OnRiwayatItemClickListener{
    fun onItemClick(item: Barang, position: Int)
}