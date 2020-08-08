package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.manajemenstok.data.local.DbHelper
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.impl.RemoteTransaksiLogicImpl
import project.manajemenstok.data.repository.TransaksiRepository
import project.manajemenstok.utils.Resource

class RiwayatViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val transaksiRepository = TransaksiRepository(
        RemoteTransaksiLogicImpl(),
        DbHelper(context)
    )

    fun getTransaksi(): MutableLiveData<Resource<ArrayList<TransaksiData>>> {
        return transaksiRepository.getDataTransaksi()
    }

    fun fetchTransaksiRepository(){
        transaksiRepository.fetchDataTransaksi()
    }

    fun getDetailTrasaksi():  MutableLiveData<Resource<ArrayList<DetailTransaksiData>>>{
        return transaksiRepository.getDataDetailTransaksi()
    }

    fun fetchDetailTransaksi(param: String){
        transaksiRepository.fetchDataDetailTransaksi(param)
    }

}