package project.manajemenstok.ui.main.adapter

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_barang_layout.view.*
import kotlinx.android.synthetic.main.item_barang_layout.view.text_jumlah
import project.manajemenstok.data.model.Barang
import kotlinx.android.synthetic.main.item_layout.view.imageViewFoto
import project.manajemenstok.R
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.main.view.activity.ActivityDetailBarang
import project.manajemenstok.ui.main.view.activity.ActivityDetailKategori
import project.manajemenstok.ui.main.viewmodel.ViewModelBarang
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Helper

import kotlin.collections.ArrayList

class ListBarangKategoriAdapter (
    private var barangs: ArrayList<Barang>,
    private var activityDetailKategori: ActivityDetailKategori,
    private var viewModelBarang: ViewModelBarang
): RecyclerView.Adapter<ListBarangKategoriAdapter.DataViewHolder>() {

    private var kategori = ArrayList<Kategori>()

    class DataViewHolder(itemView: View, val dataAdapter: ListBarangKategoriAdapter, val activityDetailKategori: ActivityDetailKategori) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var objBarang = Barang()
        fun bind(barang: Barang, dataKategori: ArrayList<Kategori>){
            objBarang = barang
            itemView.text_nama_barang.text = barang.namaBarang.capitalize()

            for (kategori in dataKategori){
                if(kategori.uuid == barang.kategori){
                    itemView.text_kategori.text = kategori.nama
                }
            }

            itemView.text_jumlah.text = Helper.getFormat(barang.jumlah)
            itemView.text_harga.text = Helper.getFormat(barang.harga)
            Glide.with(itemView.imageViewFoto.context).load(objBarang.foto).into(itemView.imageViewFoto)

            itemView.icon_delete.setOnClickListener(this)
            itemView.text_nama_barang.setOnClickListener(this)
            itemView.text_kategori.setOnClickListener(this)
            itemView.imageViewFoto.setOnClickListener(this)
            itemView.content_bottom.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when (v.id){
                R.id.icon_delete->{
//                    Toast.makeText(v.context, "Dihapus", Toast.LENGTH_LONG).show()
                    dataAdapter.hapusBarang(objBarang)
                }
                R.id.text_nama_barang,
                R.id.text_kategori,
                R.id.imageViewFoto,
                R.id.content_bottom->{
                    val bundle = Bundle()
                    val detailBarangIntent =  Intent(v.context, ActivityDetailBarang::class.java)
                    detailBarangIntent.putExtra("dataBarang", objBarang)
                    detailBarangIntent.putExtra("dataKategori", activityDetailKategori.getListKategori())
//                    startActivityForResult(activity, detailBarangIntent, Constants.RequestCodeIntent.DETAIL_BARANG, bundle)
                    startActivity(activityDetailKategori, detailBarangIntent, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_barang_layout, parent,
                false
            ), this, activityDetailKategori
        )

    override fun getItemCount(): Int = barangs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(barangs[position], kategori)

    fun addData(list: List<Barang>) {
        barangs.addAll(list)
    }

    fun setData(list: List<Barang>, dataKategori: ArrayList<Kategori>) {
        kategori = dataKategori
        barangs.clear()
        barangs.addAll(list)
    }

    fun hapusBarang(barang: Barang){
        var builder = AlertDialog.Builder(activityDetailKategori, R.style.CustomDialogTheme)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Anda Yakin ingin menghapus " + barang.namaBarang + " ?")

        builder.setPositiveButton("HAPUS") { _, _ ->
            barang.isDeleted = Constants.DeleteStatus.DELETED
            viewModelBarang.saveBarang(barang)
            Toast.makeText(activityDetailKategori, "Barang berhasil dihapus", Toast.LENGTH_LONG).show()
            activityDetailKategori.onResume()
        }

        builder.setNegativeButton("BATAL") { _, _ ->
        }

        builder.show()
    }

}