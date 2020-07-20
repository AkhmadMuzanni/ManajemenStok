package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_konfirmasi_pembelian_layout.view.*
import project.manajemenstok.data.model.Barang
import kotlinx.android.synthetic.main.item_layout.view.textViewNamaBarang
import project.manajemenstok.R

class KonfirmasiPembelianAdapter (
    private val barangs: ArrayList<Barang>
): RecyclerView.Adapter<KonfirmasiPembelianAdapter.DataViewHolder>() {

    class DataViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        var objBarang = Barang()
        var pos = 0

        fun bind(barang: Barang, position: Int){
            objBarang = barang
            pos = position

            itemView.textViewNamaBarang.setText(barang.namaBarang.capitalize())
            itemView.text_harga_satuan.setText(barang.harga.toString())
            itemView.text_harga_total.setText(barang.total.toString())
            itemView.text_jumlah_barang.setText(barang.jumlah.toString() + "x")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_konfirmasi_pembelian_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = barangs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(barangs[position], position)

    fun addData(list: List<Barang>) {
        barangs.addAll(list)
    }

    fun setData(list: List<Barang>) {
        barangs.clear()
        barangs.addAll(list)
    }

}