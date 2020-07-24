package project.manajemenstok.data.local.logic

import android.database.Cursor
import project.manajemenstok.data.model.DetailPembelian
import project.manajemenstok.data.model.Pembelian

interface PembelianLogic{
    fun getPembelian(): Cursor
    fun getTempDataPembelian(): ArrayList<DetailPembelian>
    fun insertTempDataPembelian(detailPembelian: DetailPembelian)
    fun setTempDataPembelian(detailPembelian: ArrayList<DetailPembelian>)
    fun setTempOngkir(ongkir: Int)
    fun getTempOngkir(): Int
    fun createPembelian(pembelian: Pembelian): Int
    fun getListPembelian(): List<Pembelian>
    fun createDetailPembelian(detailPembelian: DetailPembelian): Int
}