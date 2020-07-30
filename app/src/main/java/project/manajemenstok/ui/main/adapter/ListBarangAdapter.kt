package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_barang_layout.view.*
import project.manajemenstok.data.model.Barang
import kotlinx.android.synthetic.main.item_layout.view.imageViewFoto
import project.manajemenstok.R

class ListBarangAdapter (
    private var barangs: ArrayList<Barang>
): RecyclerView.Adapter<ListBarangAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(barang: Barang){
            itemView.text_nama_barang.text = barang.namaBarang.capitalize()
            itemView.text_kategori.text = "Celana Anak"
            itemView.text_jumlah.text = barang.jumlah.toString()
            itemView.text_harga.text = barang.harga.toString()
            Glide.with(itemView.imageViewFoto.context).load(barang.foto).into(itemView.imageViewFoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_barang_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = barangs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(barangs[position])

    fun addData(list: List<Barang>) {
        barangs.addAll(list)
    }

    fun setData(list: List<Barang>) {
        barangs.clear()
        barangs.addAll(list)
    }

}