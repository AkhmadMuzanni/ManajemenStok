package project.manajemenstok.data.remote.logic

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import project.manajemenstok.data.model.*
import project.manajemenstok.utils.Resource

interface RemoteKategoriLogic {
    fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>>
    fun fetchKategori()
    fun setKategori(listKategori: ArrayList<Kategori>)
    fun updateKategori(kategori: Kategori)
    fun getImageUrl(): MutableLiveData<String>
    fun fetchImageUrl(path: String)
    fun setImageUrl(url: String)
    fun uploadImage(imageUri: Uri, path: String)
    fun createKategori(kategori: Kategori): String
    fun syncKategori()
    fun countKategoriOnBarang(listBarang: ArrayList<Barang>)
    fun updateBarang(barang: Barang)
    fun getBarangKategori(): MutableLiveData<Resource<ArrayList<Barang>>>
    fun fetchBarangKategori(uuidKategori: String)
    fun setBarangKategori(listBarang: ArrayList<Barang>)
    fun fetchBarangKategori(uuidKategori: String, query: String)
    fun syncKategori(query: String)
    fun countKategoriOnBarang(listBarang: ArrayList<Barang>, query: String)
}