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
        return Single.just(cursor.parseList(classParser<Barang>()))

    }

}