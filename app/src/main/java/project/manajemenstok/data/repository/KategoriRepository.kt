package project.manajemenstok.data.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.logic.RemoteKategoriLogic
import project.manajemenstok.utils.Resource

class KategoriRepository(private val remoteKategori: RemoteKategoriLogic) {

    fun fetchKategori(){
        remoteKategori.fetchKategori()
    }

    fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>> {
        return remoteKategori.getKategori()
    }

    fun updateKategori(kategori: Kategori){
        remoteKategori.updateKategori(kategori)
    }

    fun uploadImage(imageUri: Uri, path: String){
        remoteKategori.uploadImage(imageUri, path)
    }

    fun getUploadResult(): MutableLiveData<String>{
        return remoteKategori.getImageUrl()
    }

    fun createKategori(kategori: Kategori): String{
        return remoteKategori.createKategori(kategori)
    }

    fun syncKategori(){
        remoteKategori.syncKategori()
    }

    fun fetchBarangKategori(uuidKategori: String){
        remoteKategori.fetchBarangKategori(uuidKategori)
    }

    fun getBarangKategori(): MutableLiveData<Resource<ArrayList<Barang>>> {
        return remoteKategori.getBarangKategori()
    }

    fun fetchBarangKategori(uuidKategori: String, query: String){
        remoteKategori.fetchBarangKategori(uuidKategori, query)
    }

    fun syncKategori(query: String){
        remoteKategori.syncKategori(query)
    }

}