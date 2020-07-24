package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_riwayat_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Pembelian
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class RiwayatAdapter (
    private val pembelians: ArrayList<Pembelian>,
    private val clickListener: OnRiwayatItemClickListener
): RecyclerView.Adapter<RiwayatAdapter.DataViewHolder>(){

    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(pembelian: Pembelian, action: OnRiwayatItemClickListener){
            itemView.tv_nama_barang.text = "xxx-yyy-yyy-"+pembelian.id.toString()
            itemView.tv_jenis_transaksi.text = "supplier-"+pembelian.idPenjual.toString()
            itemView.tv_mount.text = "Rp. "+ getFormat(pembelian.totalPembelian)
            itemView.tv_count.text = getFormat(pembelian.ongkir)
            itemView.tv_datetime.text = pembelian.tglPembelian.toString()
            itemView.setOnClickListener {
                action.onItemClick(pembelian, adapterPosition)
            }
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

    override fun getItemCount(): Int = pembelians.size

    override fun onBindViewHolder(holder: RiwayatAdapter.DataViewHolder, position: Int) =
        holder.bind(pembelians[position], clickListener)

    fun addData(list: List<Pembelian>) {
        pembelians.addAll(list)
    }

}

interface OnRiwayatItemClickListener{
    fun onItemClick(item: Pembelian, position: Int)
}