package project.manajemenstok.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import project.manajemenstok.data.local.logic.BarangLogic
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.remote.logic.RemoteBrangLogic


class BarangRepository(private val remoteBarang: RemoteBrangLogic, private val localBarang: BarangLogic) {

    fun getBarangs(): Single<List<Barang>>{
        return remoteBarang.getBarangs()
    }

    fun getBarangsLocal(): Single<List<Barang>> {
        val cursor  = localBarang.getBarang()
        return Single.just(cursor.parseList(classParser()))
    }

    fun setBarangFromRemote(dataBarang: List<Barang>){
        localBarang.setBarangFromRemote(dataBarang)
    }

    fun addTempBarang(dataBarang: Barang){
        localBarang.insertTempBarang(dataBarang)
    }

    fun getTempBarang(): ArrayList<Barang>{
        return localBarang.getTempBarang()
    }

    fun setTempBarang(dataBarang: ArrayList<Barang>){
        localBarang.setTempBarang(dataBarang)
    }

    fun createBarang(barang: Barang): String{
//        return localBarang.createBarang(barang)
        return remoteBarang.createBarang(barang)
    }

    fun updateBarang(barang: Barang){
//        localBarang.updateBarang(barang)
        remoteBarang.updateBarang(barang)
    }

    fun getBarangById(id: Int): Barang{
        return localBarang.getBarangById(id)
    }

    fun deleteTempBarang(position: Int){
        localBarang.deleteTempBarang(position)
    }

    fun getBarangUsed(): ArrayList<Barang>{
        return localBarang.getBarangUsed()
    }

    fun getUnusedBarang(barangUsed: ArrayList<Barang>): ArrayList<Barang>{
        return localBarang.getUnusedBarang(barangUsed)
    }

    fun getDbReference(query: String): DatabaseReference{
        return remoteBarang.getDbReference(query)
    }

    fun getLiveBarang(): MutableLiveData<ArrayList<Barang>>{
        return remoteBarang.getLiveBarang()
    }

    fun fetchLiveBarang(){
        remoteBarang.fetchLiveBarang()
    }

    fun fetchUnusedBarang(barangUsed: ArrayList<Barang>){
        remoteBarang.fetchUnusedBarang(barangUsed)
    }

    fun getUnusedBarang(): MutableLiveData<ArrayList<Barang>>{
        return remoteBarang.getUnusedBarang()
    }

    fun fetchBarangById(id: String){
        remoteBarang.fetchBarangById(id)
    }

    fun getBarangById(): MutableLiveData<Barang>{
        return remoteBarang.getBarangById()
    }

}