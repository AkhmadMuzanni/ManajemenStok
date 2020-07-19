package project.manajemenstok.data.repository

import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import project.manajemenstok.data.local.BarangLogic
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.remote.RemoteBrangLogic


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

    fun createBarang(barang: Barang): Int{
        return localBarang.createBarang(barang)
    }

    fun updateBarang(barang: Barang){
        localBarang.updateBarang(barang)
    }

    fun getBarangById(id: Int): Barang{
        return localBarang.getBarangById(id)
    }

}