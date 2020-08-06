package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.impl.RemoteKategoriLogicImpl
import project.manajemenstok.data.repository.KategoriRepository
import project.manajemenstok.utils.Resource

class KategoriViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val kategoriRepository = KategoriRepository(
        RemoteKategoriLogicImpl()
    )

    fun fetchKategori(){
        kategoriRepository.fetchKategori()
    }

    fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>> {
        return kategoriRepository.getKategori()
    }

    fun saveKategori(kategori: Kategori){
        kategoriRepository.updateKategori(kategori)
    }

    fun getUploadResult(): MutableLiveData<String> {
        return kategoriRepository.getUploadResult()
    }

    fun uploadImage(imageUri: Uri, path: String){
        kategoriRepository.uploadImage(imageUri, path)
    }

    fun createKategori(kategori: Kategori): String{
        return kategoriRepository.createKategori(kategori)
    }

    fun syncKategori(){
        kategoriRepository.syncKategori()
    }
}