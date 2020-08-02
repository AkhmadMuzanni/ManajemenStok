package project.manajemenstok.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import project.manajemenstok.data.local.logic.BarangLogic
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.remote.logic.RemoteBarangLogic


class BarangRepository(private val remoteBarang: RemoteBarangLogic, private val localBarang: BarangLogic) {

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

    fun fetchBarangTransaksi(listBarang: ArrayList<Barang>){
        remoteBarang.fetchBarangTransaksi(listBarang)
    }

    fun getBarangTransaksi(): MutableLiveData<ArrayList<Barang>>{
        return remoteBarang.getBarangTransaksi()
    }

    fun addTempBarangPembelian(dataBarangPembelian: Barang){
        localBarang.insertTempBarangPembelian(dataBarangPembelian)
    }

    fun getTempBarangPembelian(): ArrayList<Barang>{
        return localBarang.getTempBarangPembelian()
    }

    fun setTempBarangPembelian(dataBarangPembelian: ArrayList<Barang>){
        localBarang.setTempBarangPembelian(dataBarangPembelian)
    }

    fun deleteTempBarangPembelian(position: Int){
        localBarang.deleteTempBarangPembelian(position)
    }

    fun addTempBarangPenjualan(dataBarangPenjualan: Barang){
        localBarang.insertTempBarangPenjualan(dataBarangPenjualan)
    }

    fun getTempBarangPenjualan(): ArrayList<Barang>{
        return localBarang.getTempBarangPenjualan()
    }

    fun setTempBarangPenjualan(dataBarangPenjualan: ArrayList<Barang>){
        localBarang.setTempBarangPenjualan(dataBarangPenjualan)
    }

    fun deleteTempBarangPenjualan(position: Int){
        localBarang.deleteTempBarangPenjualan(position)
    }

    fun getBarangPembelianUsed(): ArrayList<Barang>{
        return localBarang.getBarangPembelianUsed()
    }

    fun getBarangPenjualanUsed(): ArrayList<Barang>{
        return localBarang.getBarangPenjualanUsed()
    }

    fun fetchImageUrl(path: String){
        remoteBarang.fetchImageUrl(path)
    }

    fun getImageUrl(): MutableLiveData<String>{
        return remoteBarang.getImageUrl()
    }

}