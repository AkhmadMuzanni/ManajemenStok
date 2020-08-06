package project.manajemenstok.data.remote.logic

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import project.manajemenstok.data.model.*
import project.manajemenstok.utils.Resource

interface RemoteKategoriLogic {
    fun getDbReference(query: String): DatabaseReference
    fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>>
    fun fetchKategori()
    fun setKategori(listKategori: ArrayList<Kategori>)
    fun updateKategori(kategori: Kategori)
    fun getImageUrl(): MutableLiveData<String>
    fun fetchImageUrl(path: String)
    fun setImageUrl(url: String)
    fun uploadImage(imageUri: Uri, path: String)
    fun getStorageReference(query: String): StorageReference
    fun createKategori(kategori: Kategori): String
    fun syncKategori()
    fun countKategoriOnBarang(listBarang: ArrayList<Barang>)
    fun updateBarang(barang: Barang)
}