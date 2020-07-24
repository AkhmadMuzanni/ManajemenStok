package project.manajemenstok.data.repository

import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import project.manajemenstok.data.local.logic.PembelianLogic
import project.manajemenstok.data.model.DetailPembelian
import project.manajemenstok.data.model.Pembelian
import project.manajemenstok.data.remote.logic.RemotePembelianLogic


class PembelianRepository(private val remotePembelian: RemotePembelianLogic, private val localPembelian: PembelianLogic) {

    fun setTempOngkir(ongkir: Int){

        localPembelian.setTempOngkir(ongkir)
    }

    fun getTempOngkir(): Int{
        return localPembelian.getTempOngkir()
    }

    fun createPembelian(pembelian: Pembelian): Int{
        return localPembelian.createPembelian(pembelian)
    }

    fun createDetailPembelian(detailPembelian: DetailPembelian): Int{
        return localPembelian.createDetailPembelian(detailPembelian)
    }

    fun getPembelian(): Single<List<Pembelian>> {
        val cursor = localPembelian.getPembelian()
        return Single.just(cursor.parseList(classParser()))
    }

}