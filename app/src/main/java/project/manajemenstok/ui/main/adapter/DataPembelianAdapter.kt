package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnFocusChangeListener {
        var objBarang = Barang()
        fun bind(barang: Barang){
            objBarang = barang
            var status = ""
            if(barang.id == 0){
                status = " (Baru)"
            }
            itemView.textViewNamaBarang.text = barang.namaBarang.capitalize() + status
            Glide.with(itemView.image_view_barang.context).load(barang.foto).into(itemView.image_view_barang)

            itemView.btn_kurang.setOnClickListener(this)
            itemView.btn_tambah.setOnClickListener(this)
            itemView.text_jumlah.onFocusChangeListener = this

        }

        override fun onClick(v: View) {
            val jml = Integer.parseInt(itemView.text_jumlah.text.toString())
            when (v.id){
                R.id.btn_tambah->{
                    itemView.text_jumlah.setText((jml+1).toString())
                    objBarang.jumlah = jml+1
                }
                R.id.btn_kurang->{
                    if(jml > 1){
                        itemView.text_jumlah.setText((jml-1).toString())
                        objBarang.jumlah = jml-1
                    }
                }
            }
        }

        override fun onFocusChange(v: View, hasFocus: Boolean) {
            when (v.id){
                R.id.text_jumlah->{
                    if(itemView.text_jumlah.text.toString() == "" && !hasFocus){
                        itemView.text_jumlah.setText("1")
                        objBarang.jumlah = 1
                    }
                }
            }
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