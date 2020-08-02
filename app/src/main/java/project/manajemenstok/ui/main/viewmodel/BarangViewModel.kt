package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.manajemenstok.data.local.DbHelper
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.impl.RemoteBarangLogicImpl
import project.manajemenstok.data.repository.BarangRepository

class BarangViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val barangRepository = BarangRepository(
        RemoteBarangLogicImpl(),
        DbHelper(context)
    )

    fun getLiveBarang(): MutableLiveData<ArrayList<Barang>> {
        return barangRepository.getLiveBarang()
    }

    fun fetchLiveBarang(){
        barangRepository.fetchLiveBarang()
    }

    fun saveBarang(barang: Barang){
        barangRepository.updateBarang(barang)
    }
}