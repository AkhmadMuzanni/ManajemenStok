package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_detail_riwayat_layout.view.*
import kotlinx.android.synthetic.main.item_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang

class DetailRiwayatAdapter(
    private val barangs: ArrayList<Barang>
): RecyclerView.Adapter<DetailRiwayatAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(barang: Barang){
            itemView.tv_item_riwayat_count.text = barang.jumlah.toString()+"x"
            itemView.tv_item_riwayat_name.text = barang.namaBarang.capitalize()
            itemView.tv_item_riwayat_price.text = "Rp. " + barang.harga.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_detail_riwayat_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = barangs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(barangs[position])

    fun addData(list: List<Barang>) {
        barangs.addAll(list)
    }

    fun getTotalHarga(barangs: List<Barang>): Int{
        var temp: Int = 0
        for (barang in barangs){
            temp += barang.harga
        }
        return temp
    }
}