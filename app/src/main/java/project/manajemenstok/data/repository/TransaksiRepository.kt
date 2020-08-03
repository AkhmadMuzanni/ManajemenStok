package project.manajemenstok.data.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import project.manajemenstok.data.local.logic.PembelianLogic
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.logic.RemoteTransaksiLogic
import project.manajemenstok.utils.Resource


class TransaksiRepository(private val remoteTransaksi: RemoteTransaksiLogic, private val localPembelian: PembelianLogic) {

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

    fun createTransaksi(transaksi: TransaksiFirebase): String{
        return remoteTransaksi.createTransaksi(transaksi)
    }

    fun createDetailTransaksi(detailTransaksiFirebase: DetailTransaksiFirebase, idDetailTransaksi: String): String{
        return remoteTransaksi.createDetailTransaksi(detailTransaksiFirebase, idDetailTransaksi)
    }

    fun fetchDataTransaksi(){
        remoteTransaksi.fetchTransaksi()
    }

    fun getDataTransaksi(): MutableLiveData<Resource<ArrayList<TransaksiData>>>{
        return remoteTransaksi.getTransaksi()
    }

    fun fetchDataDetailTransaksi(param: String){
        remoteTransaksi.fetchDetailTransaksi(param)
    }

    fun getDataDetailTransaksi(): MutableLiveData<Resource<ArrayList<DetailTransaksiData>>>{
        return remoteTransaksi.getDetailTransaksi()
    }


}