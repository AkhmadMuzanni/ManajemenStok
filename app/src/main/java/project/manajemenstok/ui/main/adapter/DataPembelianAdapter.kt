package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.manajemenstok.data.model.Barang
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.item_layout.view.imageViewFoto
import kotlinx.android.synthetic.main.item_layout.view.textViewNamaBarang
import kotlinx.android.synthetic.main.item_pembelian_layout.view.*
import project.manajemenstok.R

class DataPembelianAdapter (
    private val barangs: ArrayList<Barang>
): RecyclerView.Adapter<DataPembelianAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(barang: Barang){
            var status = ""
            if(barang.id == 0){
                status = " (Baru)"
            }
            itemView.textViewNamaBarang.text = barang.namaBarang.capitalize() + status
            Glide.with(itemView.image_view_barang.context).load(barang.foto).into(itemView.image_view_barang)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pembelian_layout, parent,
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