package project.manajemenstok.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_kategori_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Kategori

class KategoriGridAdapter(
    context: Context,
    private var resource: Int,
    private var objects: ArrayList<Kategori>) :
    ArrayAdapter<Kategori>(context, resource, objects) {

    var listKategori = ArrayList<Kategori>()

    init {
        listKategori = objects
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_kategori_layout, parent, false
        )

        view.text_kategori.setText(listKategori[position].nama)
        view.text_jml_kategori.setText(listKategori[position].jumlah.toString())
        Glide.with(view.image_kategori.context).load(context.resources.getString(R.string.defaultImageIcon)).into(view.image_kategori)

        return view
    }

    fun setData(listKategori: ArrayList<Kategori>){
        objects.clear()
        objects.addAll(listKategori)
    }


}