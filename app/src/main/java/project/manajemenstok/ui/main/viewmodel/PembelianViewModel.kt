package project.manajemenstok.ui.main.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
import project.manajemenstok.ui.main.view.KonfirmasiPembelianActivity
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Resource

class PembelianViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val barangRepository = BarangRepository(
        RemoteBarangLogicImpl(),
        DbHelper(context)
    )
    private val barangs = MutableLiveData<Resource<List<Barang>>>()
    private val penjuals = MutableLiveData<Resource<List<Penjual>>>()
    private val pembelians = MutableLiveData<Resource<List<Pembelian>>>()

    private val compositeDisposable = CompositeDisposable()

    private val penjualRepository = CustomerRepository(
        RemoteCustomerLogicImpl(),
        DbHelper(context)
    )

    private val pembelianRepository = TransaksiRepository(
        RemoteTransaksiLogicImpl(),
        DbHelper(context)
    )

    init {
        checkBarang()
        fetchPenjual()
        fetchPembelian()
    }

    @SuppressLint("CheckResult")
    private fun getDataRemote(){
        barangRepository.getBarangs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                barangRepository.setBarangFromRemote(it)
                fetchBarang()
            }, { throwable ->
                barangs.postValue(Resource.error("Something Went Wrong", null))
            })
    }

    @SuppressLint("CheckResult")
    private fun fetchBarang(){
        barangs.postValue(Resource.loading(null))
        if(is_remote){
            compositeDisposable.add(
                barangRepository.getBarangsLocal()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ barangList ->
                        barangs.postValue(Resource.success(barangList))
                    }, { throwable ->
                        barangs.postValue(Resource.error("Something Went Wrong", null))
                    })
            )
        }
        else {
            barangs.postValue(Resource.success(null))
        }

    }

    private fun checkBarang(){
        barangs.postValue(Resource.loading(null))
        compositeDisposable.add(
            barangRepository.getBarangsLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ barangList ->
                    if(barangList.isEmpty()){
                        getDataRemote()
                    } else {
                        fetchBarang()
                    }
                }, { throwable ->
                    barangs.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getBarangs(): LiveData<Resource<List<Barang>>> {
        return barangs
    }

    fun addTempBarang(dataBarang: Barang){
//        barangRepository.addTempBarang(dataBarang)
        barangRepository.addTempBarangPembelian(dataBarang)
    }

    fun getTempBarang(): ArrayList<Barang>{
//        return barangRepository.getTempBarang()
        return barangRepository.getTempBarangPembelian()
    }

    private fun fetchPenjual(){
        penjuals.postValue(Resource.loading(null))
        compositeDisposable.add(
            penjualRepository.getPenjualLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ penjualList ->
                    penjuals.postValue(Resource.success(penjualList))
                }, { throwable ->
                    penjuals.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    fun setTempBarang(dataBarang: ArrayList<Barang>){
//        barangRepository.setTempBarang(dataBarang)
        barangRepository.setTempBarangPembelian(dataBarang)
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

    fun getPenjual(): LiveData<Resource<List<Penjual>>> {
        return penjuals
    }

    fun setTempPenjual(dataPenjual: String){
        penjualRepository.setTempPenjual(dataPenjual)
    }

    fun getTempPenjual(): String{
        return penjualRepository.getTempPenjual()
    }

    /* // Simpan Pembelian Local
    fun simpanPembelian(bundle: Bundle): Boolean{
        var dataPenjual = Penjual()
        dataPenjual.namaPenjual = bundle.getBundle("dataPenjual")!!.getString("namaPenjual")!!
        dataPenjual.noTelp = bundle.getBundle("dataPenjual")!!.getString("noTelp")!!

        val idPenjual = penjualRepository.createPenjual(dataPenjual)

        var dataPembelian = Pembelian()
        dataPembelian.idPenjual = idPenjual
        dataPembelian.tglPembelian = LocalDateTime.now().toString()
        dataPembelian.ongkir = Integer.parseInt(bundle.getString("dataOngkir")!!)
        dataPembelian.totalPembelian = Integer.parseInt(bundle.getString("dataTotal")!!)
        dataPembelian.metode = Constants.MetodePembayaran.CASH
        dataPembelian.jenisTransaksi = Constants.JenisTransaksiValue.PEMBELIAN

        val idPembelian = pembelianRepository.createPembelian(dataPembelian)

        val dataDetailPembelian = bundle.getSerializable("dataBarang") as ArrayList<Barang>

        for(detail in dataDetailPembelian){
            val detailPembelian = DetailPembelian()
            if(detail.id == 0){
                val idBarang = barangRepository.createBarang(detail)
                detailPembelian.idBarang = idBarang
            } else {
                detailPembelian.idBarang = detail.id
                var existedBarang = barangRepository.getBarangById(detail.id)
                existedBarang.jumlah += detail.jumlah
                existedBarang.total += detail.total
                existedBarang.harga = existedBarang.total / existedBarang.jumlah

                barangRepository.updateBarang(existedBarang)
            }
            detailPembelian.idPembelian = idPembelian
            detailPembelian.harga = detail.harga
            detailPembelian.jumlah = detail.jumlah
            detailPembelian.total = detail.total

            pembelianRepository.createDetailPembelian(detailPembelian)
        }

        Toast.makeText(context, "Berhasil Menyimpan Pembelian", Toast.LENGTH_LONG).show()
        return true
    }*/

//    SimpanPembelian to Firebase
    fun simpanPembelian(bundle: Bundle, activity: KonfirmasiPembelianActivity): Boolean{
        var dataPenjual = KlienFirebase()
        dataPenjual.nama = bundle.getBundle("dataPenjual")!!.getString("namaPenjual")!!
        dataPenjual.noTelp = bundle.getBundle("dataPenjual")!!.getString("noTelp")!!

        val idPenjual = penjualRepository.createKlien(dataPenjual)
        val dateNow = LocalDateTime.now()
        var dataPembelian = TransaksiFirebase()
        dataPembelian.idKlien = idPenjual
//        dataPembelian.tglTransaksi = LocalDateTime.now().toString()
        dataPembelian.tglTransaksi = dateNow.format(DateTimeFormatter.ofPattern("dd MMM yyy hh:mm:ss"))
        dataPembelian.ongkir = Integer.parseInt(bundle.getString("dataOngkir")!!)
        dataPembelian.totalTransaksi = Integer.parseInt(bundle.getString("dataTotal")!!)
        dataPembelian.metode = Constants.MetodePembayaran.CASH
        dataPembelian.jenisTransaksi = Constants.JenisTransaksiValue.PEMBELIAN

        val idPembelian = pembelianRepository.createTransaksi(dataPembelian)
//
        val dataDetailPembelian = bundle.getSerializable("dataBarang") as ArrayList<Barang>

        var idDetailPembelian = ""

        barangRepository.getBarangTransaksi().observe(activity, Observer {

            for((index, detail) in it.withIndex()){
                val detailPenjualan = DetailTransaksiFirebase()
                if(detail.uuid == ""){
                    val idBarang = barangRepository.createBarang(detail)
                    detailPenjualan.idBarang = idBarang
                } else {
                    detailPenjualan.idBarang = detail.uuid
                    var existedBarang = detail
                    existedBarang.jumlah += dataDetailPembelian[index].jumlah
                    existedBarang.total += dataDetailPembelian[index].total
                    existedBarang.harga = existedBarang.total / existedBarang.jumlah

                    barangRepository.updateBarang(existedBarang)
                }
                detailPenjualan.idTransaksi = idPembelian
                detailPenjualan.harga = dataDetailPembelian[index].harga
                detailPenjualan.jumlah = dataDetailPembelian[index].jumlah
                detailPenjualan.total = dataDetailPembelian[index].total

                idDetailPembelian = pembelianRepository.createDetailTransaksi(detailPenjualan, idDetailPembelian)
            }
        })

        barangRepository.fetchBarangTransaksi(dataDetailPembelian)

        Toast.makeText(context, "Berhasil Menyimpan Pembelian", Toast.LENGTH_LONG).show()
        return true
    }

    fun deleteTempBarang(position: Int){
//        barangRepository.deleteTempBarang(position)
        barangRepository.deleteTempBarangPembelian(position)
    }

    fun getSubtotal(): Int{
//        val dataPembelian = barangRepository.getTempBarang()
        val dataPembelian = barangRepository.getTempBarangPembelian()
        var subtotal = 0
        for(detail in dataPembelian){
            subtotal += detail.total
        }
        return subtotal
    }

    fun getBarangUsed(): ArrayList<Barang>{
        return barangRepository.getBarangPembelianUsed()
    }

    fun getUnusedBarang(barangUsed: ArrayList<Barang>): ArrayList<Barang>{
        return barangRepository.getUnusedBarang(barangUsed)
    }

    @SuppressLint("CheckResult")
    fun fetchPembelian(){
        pembelians.postValue(Resource.loading(null))
        compositeDisposable.add(
            pembelianRepository.getPembelian()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pembelianList ->
                    pembelians.postValue(Resource.success(pembelianList))
                }, { throwable ->
                    pembelians.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    fun getPembelian(): LiveData<Resource<List<Pembelian>>>{
        return pembelians
    }

    fun getLiveBarang(): MutableLiveData<ArrayList<Barang>>{
        return barangRepository.getLiveBarang()
    }

    fun fetchLiveBarang(){
        barangRepository.fetchLiveBarang()
    }

    fun getUnusedBarang(): MutableLiveData<ArrayList<Barang>>{
        return barangRepository.getUnusedBarang()
    }

    fun fetchUnusedBarang(barangUsed: ArrayList<Barang>){
        barangRepository.fetchUnusedBarang(barangUsed)
    }

}