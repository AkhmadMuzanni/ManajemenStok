package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import project.manajemenstok.data.local.DbHelper
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.impl.RemoteBarangLogicImpl
import project.manajemenstok.data.remote.impl.RemoteTransaksiLogicImpl
import project.manajemenstok.data.remote.impl.RemoteCustomerLogicImpl
import project.manajemenstok.data.repository.BarangRepository
import project.manajemenstok.data.repository.TransaksiRepository
import project.manajemenstok.data.repository.CustomerRepository
import project.manajemenstok.ui.main.view.activity.ActivityKonfirmasiPembelian
import project.manajemenstok.utils.Constants

class ViewModelPenjualan (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val barangRepository = BarangRepository(
        RemoteBarangLogicImpl(),
        DbHelper(context)
    )

    private val pembelianRepository = TransaksiRepository(
        RemoteTransaksiLogicImpl(),
        DbHelper(context)
    )

    private val penjualRepository = CustomerRepository(
        RemoteCustomerLogicImpl(),
        DbHelper(context)
    )

    fun addTempBarang(dataBarang: Barang){
//        barangRepository.addTempBarang(dataBarang)
        barangRepository.addTempBarangPenjualan(dataBarang)
    }

    fun getTempBarang(): ArrayList<Barang>{
        return barangRepository.getTempBarangPenjualan()
    }

    fun deleteTempBarang(position: Int){
//        barangRepository.deleteTempBarang(position)
        barangRepository.deleteTempBarangPenjualan(position)

    }

    fun getTotalTransaksi(): Int{
        val dataBarang = getTempBarang()
        var total = 0
        for (barang in dataBarang) {
            total += barang.total
        }
        total += getTempOngkir()
        return total
    }

    fun setTempOngkir(ongkir: Int){
        pembelianRepository.setTempOngkir(ongkir)
    }

    fun getTempOngkir(): Int{
        return pembelianRepository.getTempOngkir()
    }

    fun setTempBarang(dataBarang: ArrayList<Barang>){
        barangRepository.setTempBarangPenjualan(dataBarang)
    }

    fun getBarangUsed(): ArrayList<Barang>{
        return barangRepository.getBarangPenjualanUsed()
    }

    fun getSubtotal(): Int{
//        val dataPembelian = barangRepository.getTempBarang()
        val dataPenjualan = barangRepository.getTempBarangPenjualan()
        var subtotal = 0
        for(detail in dataPenjualan){
            subtotal += detail.total
        }
        return subtotal
    }

    fun simpanPenjualan(bundle: Bundle, activityKonfirmasiPembelian: ActivityKonfirmasiPembelian): Boolean{
        var dataPembeli = KlienFirebase()
        dataPembeli.nama = bundle.getBundle("dataPenjual")!!.getString("namaPenjual")!!
        dataPembeli.noTelp = bundle.getBundle("dataPenjual")!!.getString("noTelp")!!

        val idPembeli = penjualRepository.createKlien(dataPembeli)
        val dateNow = LocalDateTime.now()

        var dataPenjualan = TransaksiFirebase()
        dataPenjualan.idKlien = idPembeli

        dataPenjualan.tglTransaksi = dateNow.format(DateTimeFormatter.ofPattern("dd MMM yyy hh:mm:ss"))
        dataPenjualan.ongkir = Integer.parseInt(bundle.getString("dataOngkir")!!)
        dataPenjualan.totalTransaksi = Integer.parseInt(bundle.getString("dataTotal")!!)
        dataPenjualan.metode = Constants.MetodePembayaran.CASH
        dataPenjualan.jenisTransaksi = Constants.JenisTransaksiValue.PENJUALAN

        val idPembelian = pembelianRepository.createTransaksi(dataPenjualan)

        val dataDetailPenjualan = bundle.getSerializable("dataBarang") as ArrayList<Barang>

        var idDetailPembelian = ""

        barangRepository.getBarangTransaksi().observe(activityKonfirmasiPembelian, Observer {

            for((index, detail) in it.withIndex()){
                val detailPenjualan = DetailTransaksiFirebase()
                if(detail.uuid == ""){
                    val idBarang = barangRepository.createBarang(detail)
                    detailPenjualan.idBarang = idBarang
                } else {
                    detailPenjualan.idBarang = detail.uuid
                    var existedBarang = detail
                    existedBarang.jumlah -= dataDetailPenjualan[index].jumlah

                    barangRepository.updateBarang(existedBarang)
                }
                detailPenjualan.idTransaksi = idPembelian
                detailPenjualan.harga = dataDetailPenjualan[index].harga
                detailPenjualan.jumlah = dataDetailPenjualan[index].jumlah
                detailPenjualan.total = dataDetailPenjualan[index].total

                idDetailPembelian = pembelianRepository.createDetailTransaksi(detailPenjualan, idDetailPembelian)
            }
        })

        barangRepository.fetchBarangTransaksi(dataDetailPenjualan)

        Toast.makeText(context, "Berhasil Menyimpan Penjualan", Toast.LENGTH_LONG).show()
        return true
    }

}