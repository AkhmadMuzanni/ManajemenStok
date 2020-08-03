package project.manajemenstok.ui.main.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_barang_layout.view.*
import kotlinx.android.synthetic.main.item_barang_layout.view.text_jumlah
import project.manajemenstok.data.model.Barang
import kotlinx.android.synthetic.main.item_layout.view.imageViewFoto
import project.manajemenstok.R
import project.manajemenstok.ui.main.view.DetailBarangActivity
import project.manajemenstok.ui.main.viewmodel.BarangViewModel
import project.manajemenstok.utils.Helper

import kotlin.collections.ArrayList

class ListBarangAdapter (
    private var barangs: ArrayList<Barang>,
    private var activity: FragmentActivity
): RecyclerView.Adapter<ListBarangAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View, val dataAdapter: ListBarangAdapter, val activity: FragmentActivity) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var objBarang = Barang()
        fun bind(barang: Barang){
            objBarang = barang
            itemView.text_nama_barang.text = barang.namaBarang.capitalize()
            itemView.text_kategori.text = "Celana Anak"
            itemView.text_jumlah.text = Helper.getFormat(barang.jumlah)
            itemView.text_harga.text = Helper.getFormat(barang.harga)
            Glide.with(itemView.imageViewFoto.context).load(objBarang.foto).into(itemView.imageViewFoto)

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when (v.id){
                R.id.container->{
                    val bundle = Bundle()
                    val detailBarangIntent =  Intent(v.context, DetailBarangActivity::class.java)
                    detailBarangIntent.putExtra("dataBarang", objBarang)
//                    startActivityForResult(activity, detailBarangIntent, Constants.RequestCodeIntent.DETAIL_BARANG, bundle)
                    startActivity(activity, detailBarangIntent, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_barang_layout, parent,
                false
            ), this, activity
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