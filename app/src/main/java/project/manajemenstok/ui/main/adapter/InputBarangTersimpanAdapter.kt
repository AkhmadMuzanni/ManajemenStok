package project.manajemenstok.ui.main.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_input_barang_baru.*
import project.manajemenstok.data.model.Barang
import kotlinx.android.synthetic.main.item_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.ui.main.fragment.FragmentInputBarangTersimpan

class InputBarangTersimpanAdapter (
    private val barangs: ArrayList<Barang>,
    private val fragment: FragmentInputBarangTersimpan
): RecyclerView.Adapter<InputBarangTersimpanAdapter.DataViewHolder>(){

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var objBarang = Barang()
        var fragmentInputData = FragmentInputBarangTersimpan()
        fun bind(barang: Barang, fragment: FragmentInputBarangTersimpan){
            objBarang = barang
            fragmentInputData = fragment

            itemView.textViewNamaBarang.text = objBarang.namaBarang.capitalize()
            itemView.textViewHargaBeli.text = "Stoke: " + objBarang.jumlah.toString()
            Glide.with(itemView.imageViewFoto.context).load(objBarang.foto).into(itemView.imageViewFoto)

            itemView.container.setOnClickListener(this)
        }

        override fun onClick(v: View) {
//            Toast.makeText(v.context, objBarang.id.toString(), Toast.LENGTH_LONG).show()
            fragmentInputData.sendData(objBarang)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = barangs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(barangs[position], fragment)

    fun addData(list: List<Barang>) {
        barangs.addAll(list)
    }

}