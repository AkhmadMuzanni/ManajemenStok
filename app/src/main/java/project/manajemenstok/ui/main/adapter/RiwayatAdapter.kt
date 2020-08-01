package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_riwayat_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.TransaksiFirebase
import project.manajemenstok.utils.Constants
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class RiwayatAdapter (
    private val transaksis: ArrayList<TransaksiFirebase>,
    private val clickListener: OnRiwayatItemClickListener
): RecyclerView.Adapter<RiwayatAdapter.DataViewHolder>(){

    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(transaksi: TransaksiFirebase, action: OnRiwayatItemClickListener){
            itemView.tv_jenis_transaksi.text = getTypeTransaksi(transaksi.jenisTransaksi)
            itemView.tv_nama_suplier.text = transaksi.idKlien.toString()
            itemView.tv_mount.text = "Rp. "+ getFormat(transaksi.totalTransaksi)
            itemView.tv_datetime.text = transaksi.tglTransaksi.toString()
            itemView.setOnClickListener {
                action.onItemClick(transaksi, adapterPosition)
            }
        }

        fun getTypeTransaksi(param: Int): String{
            var temp = ""
            if(param == Constants.JenisTransaksiValue.PEMBELIAN){
                temp = "Pembelian"
            }else if(param == Constants.JenisTransaksiValue.PENJUALAN){
                temp = "Penjualan"
            }

            return temp
        }

        fun getFormat(int: Int): String{
            val idLocale = Locale("id", "ID")
            val nf = NumberFormat.getNumberInstance(idLocale)
            return nf.format(int)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_riwayat_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = transaksis.size

    override fun onBindViewHolder(holder: RiwayatAdapter.DataViewHolder, position: Int) =
        holder.bind(transaksis[position], clickListener)

    fun addData(list: List<TransaksiFirebase>) {
        transaksis.addAll(list)
    }

}

interface OnRiwayatItemClickListener{
    fun onItemClick(item: TransaksiFirebase, position: Int)
}