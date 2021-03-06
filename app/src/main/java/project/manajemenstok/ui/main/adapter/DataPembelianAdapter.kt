package project.manajemenstok.ui.main.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.manajemenstok.data.model.Barang
import kotlinx.android.synthetic.main.item_layout.view.textViewNamaBarang
import kotlinx.android.synthetic.main.item_pembelian_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.ui.main.view.fragment.FragmentPembelian
import project.manajemenstok.ui.main.viewmodel.ViewModelPembelian
import project.manajemenstok.utils.NumberTextWatcher
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DataPembelianAdapter (
    private val barangs: ArrayList<Barang>,
    private val pembelianViewModel: ViewModelPembelian,
    private val fragment: FragmentPembelian
): RecyclerView.Adapter<DataPembelianAdapter.DataViewHolder>() {

    class DataViewHolder(
        itemView: View, pembelianViewModel: ViewModelPembelian, fragment: FragmentPembelian, dataAdapter: DataPembelianAdapter
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnFocusChangeListener {
        var objBarang = Barang()
        var pos = 0
        val viewModel = pembelianViewModel
        val fragment = fragment
        val dataAdapter = dataAdapter

        fun bind(barang: Barang, position: Int){
            objBarang = barang
            pos = position

            var status = ""
//            if(barang.id == 0){
//                status = " (Baru)"
//            }
            if(barang.uuid == ""){
                status = " (Baru)"
            }
            itemView.textViewNamaBarang.setText(barang.namaBarang.capitalize() + status)
            itemView.input_harga_satuan.setText(dataAdapter.getFormat(barang.harga))
            itemView.input_harga_total.setText(dataAdapter.getFormat(barang.total))
            itemView.text_jumlah.setText(dataAdapter.getFormat(barang.jumlah))
            Glide.with(itemView.image_view_barang.context).load(barang.foto).into(itemView.image_view_barang)

            itemView.btn_kurang.setOnClickListener(this)
            itemView.btn_tambah.setOnClickListener(this)
            itemView.delete_icon.setOnClickListener(this)
            itemView.text_jumlah.onFocusChangeListener = this

            textChangedListener()

        }

        override fun onClick(v: View) {
            val jml = dataAdapter.getAngka(itemView.text_jumlah.text.toString())
            when (v.id){
                R.id.btn_tambah->{
                    itemView.text_jumlah.setText(dataAdapter.getFormat(jml+1))
                    objBarang.jumlah = jml+1
                }
                R.id.btn_kurang->{
                    if(jml > 1){
                        itemView.text_jumlah.setText(dataAdapter.getFormat(jml-1))
                        objBarang.jumlah = jml-1
                    }
                }
                R.id.delete_icon->{
                    Toast.makeText(itemView.context, "Berhasil Dihapus", Toast.LENGTH_LONG).show()
                    viewModel.deleteTempBarang(pos)
                    dataAdapter.setData(viewModel.getTempBarang())
                    dataAdapter.notifyItemRemoved(pos)
                    dataAdapter.notifyItemRangeChanged(pos, viewModel.getTempBarang().size)
                    updateTotalTransaksi()
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

        fun textChangedListener(){
            itemView.input_harga_satuan.addTextChangedListener(object: NumberTextWatcher(itemView.input_harga_satuan){
                override fun afterTextChanged(s: Editable) {
                    super.afterTextChanged(s)

                    var harga = 0
                    val jml = dataAdapter.getAngka(itemView.text_jumlah.text.toString())
                    val textHarga = itemView.input_harga_satuan.text.toString()

                    if(!textHarga.equals("")){
                        harga = dataAdapter.getAngka(textHarga)
                    }
                    itemView.input_harga_total.setText(dataAdapter.getFormat(jml*harga))

                    objBarang.harga = harga
                    objBarang.total = jml*harga

                    updateTotalTransaksi()
                }
            })

            itemView.text_jumlah.addTextChangedListener(object: NumberTextWatcher(itemView.text_jumlah){
                override fun afterTextChanged(s: Editable) {
                    super.afterTextChanged(s)

                    var harga = 0
                    var jml = 0
                    val textJumlah = itemView.text_jumlah.text.toString()
                    if(!textJumlah.equals("")){
                        jml = dataAdapter.getAngka(textJumlah)
                    }

                    if(!itemView.input_harga_satuan.text.toString().equals("")){
                        harga = dataAdapter.getAngka(itemView.input_harga_satuan.text.toString())
                    }

                    itemView.input_harga_total.setText(dataAdapter.getFormat(jml*harga))

                    objBarang.harga = harga
                    objBarang.total = jml*harga
                    objBarang.jumlah = jml

                    updateTotalTransaksi()
                }
            })

            itemView.text_jumlah.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    updateTotalTransaksi()
                }
            })
        }

        fun updateTotalTransaksi(){
            val total = viewModel.getTotalTransaksi()
            fragment.activity?.findViewById<TextView>(R.id.text_input_total)?.setText(dataAdapter.getFormat(total))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pembelian_layout, parent,
                false
            ), pembelianViewModel, fragment, this
        )

    override fun getItemCount(): Int = barangs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(barangs[position], position)

    fun addData(list: List<Barang>) {
        barangs.addAll(list)
    }

    fun setData(list: List<Barang>) {
        barangs.clear()
        barangs.addAll(list)
    }

    fun getAngka(string: String): Int{
        val idLocale = Locale("id", "ID")
        val nf = NumberFormat.getNumberInstance(idLocale)
        return nf.parse(string).toInt()
    }

    fun getFormat(int: Int): String{
        val idLocale = Locale("id", "ID")
        val nf = NumberFormat.getNumberInstance(idLocale)
        return nf.format(int)
    }

}