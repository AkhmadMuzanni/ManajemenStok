package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.threeten.bp.LocalDateTime
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

class RiwayatViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val transaksiRepository = TransaksiRepository(
        RemoteTransaksiLogicImpl(),
        DbHelper(context)
    )

    fun getTransaksi(): MutableLiveData<Resource<ArrayList<TransaksiFirebase>>> {
        return transaksiRepository.getDataTransaksi()
    }

    fun fetchTransaksiRepository(){
        transaksiRepository.fetchDataTransaksi()
    }

    fun getDetailTrasaksi():  MutableLiveData<Resource<ArrayList<DetailTransaksiFirebase>>>{
        return transaksiRepository.getDataDetailTransaksi()
    }

    fun fetchDetailTransaksi(param: String){
        transaksiRepository.fetchDataDetailTransaksi(param)
    }

}