package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_kategori_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.main.fragment.FragmentBarang
import project.manajemenstok.ui.main.viewmodel.BarangViewModel

import kotlin.collections.ArrayList

class ListKategoriAdapter (
    private var kategori: ArrayList<Kategori>,
    private var fragment: FragmentBarang,
    private var barangViewModel: BarangViewModel,
    private val MAX_LENGTH: Int = 5

): RecyclerView.Adapter<ListKategoriAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View, val dataAdapter: ListKategoriAdapter, val fragment: FragmentBarang) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var objKategori = Kategori()
        fun bind(kategori: Kategori){
            objKategori = kategori

            itemView.text_kategori.setText(kategori.nama)
            itemView.text_jml_kategori.setText(kategori.jumlah.toString())
            Glide.with(itemView.image_kategori.context).load(kategori.foto).into(itemView.image_kategori)

        }

        override fun onClick(v: View) {
            when (v.id){

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_kategori_layout, parent,
                false
            ), this, fragment
        )

//    override fun getItemCount(): Int = kategori.size
    override fun getItemCount(): Int {
        if(kategori.size < MAX_LENGTH){
            return kategori.size
        } else {
            return MAX_LENGTH
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(kategori[position])

    fun addData(list: List<Kategori>) {
        kategori.addAll(list)
    }

    fun setData(list: List<Kategori>) {
        kategori.clear()
        kategori.addAll(list)
    }

}