package project.manajemenstok.data.remote.logic

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import io.reactivex.Single
import project.manajemenstok.data.model.Barang

interface RemoteBarangLogic {
    fun getBarangs(): Single<List<Barang>>
    fun getDbReference(query: String): DatabaseReference
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
}