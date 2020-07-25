package project.manajemenstok.data.remote.logic

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import io.reactivex.Single
import project.manajemenstok.data.model.Barang

interface RemoteBrangLogic {
    fun getBarangs(): Single<List<Barang>>
    fun getDbReference(query: String): DatabaseReference
    fun fetchLiveBarang()
    fun getLiveBarang(): MutableLiveData<ArrayList<Barang>>
    fun setLiveBarang(listBarang: ArrayList<Barang>)
    fun getUnusedBarang(): MutableLiveData<ArrayList<Barang>>
    fun fetchUnusedBarang(barangUsed: ArrayList<Barang>)
    fun setUnusedBarang(listBarang: ArrayList<Barang>)
}