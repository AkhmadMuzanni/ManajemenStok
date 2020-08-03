package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_detail_riwayat_layout.view.*
import kotlinx.android.synthetic.main.item_layout.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.model.DetailTransaksiData
import project.manajemenstok.data.model.DetailTransaksiFirebase
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailRiwayatAdapter(
    private val detailTransaksis: ArrayList<DetailTransaksiData>
): RecyclerView.Adapter<DetailRiwayatAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(detailTransaksi: DetailTransaksiData){
            itemView.tv_item_riwayat_count.text = detailTransaksi.jumlah.toString()+"x"
            itemView.tv_item_riwayat_name.text = detailTransaksi.namaBarang.capitalize()
            itemView.tv_item_riwayat_price.text = "Rp. "+ getFormat(detailTransaksi.harga)
            itemView.tv_item_riwayat_total_price.text = "Rp. " + getFormat(detailTransaksi.total)
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
                R.layout.item_detail_riwayat_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = detailTransaksis.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(detailTransaksis[position])

    fun addData(list: List<DetailTransaksiData>) {
        detailTransaksis.clear()
        detailTransaksis.addAll(list)
    }

}