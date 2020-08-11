package project.manajemenstok.data.remote.logic

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import project.manajemenstok.data.model.Barang

interface RemoteBarangLogic {
    fun getBarangs(): Single<List<Barang>>
    fun fetchLiveBarang()
    fun getLiveBarang(): MutableLiveData<ArrayList<Barang>>
    fun setLiveBarang(listBarang: ArrayList<Barang>)
    fun getUnusedBarang(): MutableLiveData<ArrayList<Barang>>
    fun fetchUnusedBarang(barangUsed: ArrayList<Barang>)
    fun setUnusedBarang(listBarang: ArrayList<Barang>)
    fun createBarang(barang: Barang): String
    fun getBarangById(): MutableLiveData<Barang>
    fun fetchBarangById(id: String)
    fun setBarangById(barang: Barang)
    fun updateBarang(barang: Barang)
    fun getBarangTransaksi(): MutableLiveData<ArrayList<Barang>>
    fun fetchBarangTransaksi(listBarang: ArrayList<Barang>)
    fun setBarangTransaksi(listBarang: ArrayList<Barang>)
    fun getImageUrl(): MutableLiveData<String>
    fun fetchImageUrl(path: String)
    fun setImageUrl(url: String)
    fun uploadImage(imageUri: Uri, path: String)
    fun fetchLiveBarang(query: String)
    fun fetchUnusedBarang(barangUsed: ArrayList<Barang>, query: String)
}