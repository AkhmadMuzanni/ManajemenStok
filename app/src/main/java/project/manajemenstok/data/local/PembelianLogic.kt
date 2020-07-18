package project.manajemenstok.data.local

import android.database.Cursor
import project.manajemenstok.data.model.DetailPembelian

interface PembelianLogic{
    fun getPembelian(): Cursor
    fun getTempDataPembelian(): ArrayList<DetailPembelian>
    fun insertTempDataPembelian(detailPembelian: DetailPembelian)
    fun setTempDataPembelian(detailPembelian: ArrayList<DetailPembelian>)
    fun setTempOngkir(ongkir: Int)
    fun getTempOngkir(): Int
}