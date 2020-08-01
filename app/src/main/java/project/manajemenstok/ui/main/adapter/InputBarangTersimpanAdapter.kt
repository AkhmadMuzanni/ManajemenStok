package project.manajemenstok.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.manajemenstok.data.model.Barang
import kotlinx.android.synthetic.main.item_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.ui.main.fragment.FragmentInputBarangTersimpan
import project.manajemenstok.utils.Constants

class InputBarangTersimpanAdapter (
    private val barangs: ArrayList<Barang>,
    private val fragment: FragmentInputBarangTersimpan,
    private val parentActivity: Int
): RecyclerView.Adapter<InputBarangTersimpanAdapter.DataViewHolder>(){

    class DataViewHolder(itemView: View, val dataAdapter: InputBarangTersimpanAdapter, val parentActivity: Int) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var objBarang = Barang()
        var fragmentInputData = FragmentInputBarangTersimpan()
        fun bind(barang: Barang, fragment: FragmentInputBarangTersimpan){
            objBarang = barang
            fragmentInputData = fragment

            itemView.textViewNamaBarang.text = objBarang.namaBarang.capitalize()
            itemView.textViewHargaBeli.text = "Stoke: " + objBarang.jumlah.toString()
            Glide.with(itemView.imageViewFoto.context).load(objBarang.foto).into(itemView.imageViewFoto)

            if(objBarang.jumlah == 0 && parentActivity == Constants.JenisTransaksiValue.PENJUALAN){
                dataAdapter.setViewEnabled(itemView.container, false)
                itemView.container.isEnabled = true
            }

            itemView.container.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if(objBarang.jumlah == 0 && parentActivity == Constants.JenisTransaksiValue.PENJUALAN){
                Toast.makeText(v.context, "Stok " + objBarang.namaBarang + " tidak mencukupi", Toast.LENGTH_LONG).show()
            } else {
                fragmentInputData.sendData(objBarang)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            ), this, parentActivity
        )

    override fun getItemCount(): Int = barangs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(barangs[position], fragment)

    fun addData(list: List<Barang>) {
        barangs.addAll(list)
    }

    fun setData(list: List<Barang>) {
        barangs.clear()
        barangs.addAll(list)
    }

    fun setViewEnabled(view: View, enabled: Boolean){
        view.isEnabled = enabled
        if(view is ViewGroup){
            val viewGroup = view
            for(element in viewGroup.children){
                setViewEnabled(element, enabled)
            }
        }
    }

}