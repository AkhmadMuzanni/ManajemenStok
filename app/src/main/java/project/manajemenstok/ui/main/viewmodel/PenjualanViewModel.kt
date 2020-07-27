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
import project.manajemenstok.data.local.DbHelper
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.impl.RemoteBarangLogicImpl
import project.manajemenstok.data.remote.impl.RemotePembelianLogicImpl
import project.manajemenstok.data.remote.impl.RemotePenjualLogicImpl
import project.manajemenstok.data.repository.BarangRepository
import project.manajemenstok.data.repository.PembelianRepository
import project.manajemenstok.data.repository.PenjualRepository
import project.manajemenstok.ui.main.view.KonfirmasiPembelianActivity
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Resource

class PenjualanViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val barangRepository = BarangRepository(
        RemoteBarangLogicImpl(),
        DbHelper(context)
    )

    private val pembelianRepository = PembelianRepository(
        RemotePembelianLogicImpl(),
        DbHelper(context)
    )

    fun addTempBarang(dataBarang: Barang){
        barangRepository.addTempBarang(dataBarang)
    }

    fun getTempBarang(): ArrayList<Barang>{
        return barangRepository.getTempBarang()
    }

    fun deleteTempBarang(position: Int){
        barangRepository.deleteTempBarang(position)
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
        barangRepository.setTempBarang(dataBarang)
    }

    fun getBarangUsed(): ArrayList<Barang>{
        return barangRepository.getBarangUsed()
    }


}