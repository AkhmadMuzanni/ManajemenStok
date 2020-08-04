package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.manajemenstok.data.local.DbHelper
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.impl.RemoteBarangLogicImpl
import project.manajemenstok.data.remote.impl.RemoteKategoriLogicImpl
import project.manajemenstok.data.repository.BarangRepository
import project.manajemenstok.data.repository.KategoriRepository
import project.manajemenstok.utils.Resource

class BarangViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val barangRepository = BarangRepository(
        RemoteBarangLogicImpl(),
        DbHelper(context)
    )

    private val kategoriRepository = KategoriRepository(
        RemoteKategoriLogicImpl()
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

    fun getUploadResult(): MutableLiveData<String> {
        return barangRepository.getUploadResult()
    }

    fun uploadImage(imageUri: Uri, path: String){
        barangRepository.uploadImage(imageUri, path)
    }

    fun fetchKategori(){
        kategoriRepository.fetchKategori()
    }

    fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>> {
        return kategoriRepository.getKategori()
    }
}