package project.manajemenstok.data.repository

import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import project.manajemenstok.data.remote.ApiHelper
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.local.DatabaseHelper


class MainRepository(private val apiHelper: ApiHelper, private val appData: DatabaseHelper) {

    fun getBarangs(): Single<List<Barang>>{
        return apiHelper.getBarangs()
    }

    fun getBarangsLocal(): Single<List<Barang>> {
        val cursor  = appData.getBarang()
        return Single.just(cursor.parseList(classParser<Barang>()))
    }

    fun setBarangFromRemote(dataBarang: List<Barang>){
        appData.setBarangFromRemote(dataBarang)
    }

}