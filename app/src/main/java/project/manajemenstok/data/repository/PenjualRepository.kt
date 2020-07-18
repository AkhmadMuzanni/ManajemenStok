package project.manajemenstok.data.repository

import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import project.manajemenstok.data.local.PenjualLogic
import project.manajemenstok.data.model.Penjual
import project.manajemenstok.data.remote.RemotePenjualLogic


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

}