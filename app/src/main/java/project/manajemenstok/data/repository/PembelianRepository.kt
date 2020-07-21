package project.manajemenstok.data.repository

import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import project.manajemenstok.data.local.PembelianLogic
import project.manajemenstok.data.local.PenjualLogic
import project.manajemenstok.data.model.DetailPembelian
import project.manajemenstok.data.model.Pembelian
import project.manajemenstok.data.model.Penjual
import project.manajemenstok.data.remote.RemotePembelianLogic
import project.manajemenstok.data.remote.RemotePenjualLogic


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

}