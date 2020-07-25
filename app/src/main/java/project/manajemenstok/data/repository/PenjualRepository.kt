package project.manajemenstok.data.repository

import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import project.manajemenstok.data.local.logic.PenjualLogic
import project.manajemenstok.data.model.KlienFirebase
import project.manajemenstok.data.model.Penjual
import project.manajemenstok.data.remote.logic.RemotePenjualLogic


class PenjualRepository(private val remotePenjual: RemotePenjualLogic, private val localPenjual: PenjualLogic) {

    fun getPenjual(): Single<List<Penjual>>{
        return remotePenjual.getPenjual()
    }

    fun getPenjualLocal(): Single<List<Penjual>> {
        val cursor  = localPenjual.getPenjual()
        return Single.just(cursor.parseList(classParser()))
    }

    fun setTempPenjual(dataPenjual: String){
        localPenjual.setTempPenjual(dataPenjual)
    }

    fun getTempPenjual(): String{
        return localPenjual.getTempPenjual()
    }

    fun createPenjual(penjual: Penjual): Int{
        return localPenjual.createPenjual(penjual)
    }

    fun getListPenjual(): List<Penjual>{
        return localPenjual.getListPenjual()
    }

    fun createKlien(klien: KlienFirebase): String{
        return remotePenjual.setKlien(klien)
    }

}