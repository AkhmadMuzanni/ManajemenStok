package project.manajemenstok.data.local.logic

import android.database.Cursor
import project.manajemenstok.data.model.Barang

interface BarangLogic{
    fun getBarang(): Cursor
    fun setBarangFromRemote(dataBarang: List<Barang>)
    fun createBarang(barang: Barang): Int
    fun updateBarang(barang: Barang)
    fun getBarangById(id: Int): Barang
    fun getBarangUsed(): ArrayList<Barang>
    fun getUnusedBarang(barangUsed: ArrayList<Barang>): ArrayList<Barang>
    fun insertTempBarangPembelian(dataBarangPembelian: Barang)
    fun getTempBarangPembelian(): ArrayList<Barang>
    fun setTempBarangPembelian(dataBarangPembelian: ArrayList<Barang>)
    fun deleteTempBarangPembelian(id: Int)
    fun insertTempBarangPenjualan(dataBarangPenjualan: Barang)
    fun getTempBarangPenjualan(): ArrayList<Barang>
    fun setTempBarangPenjualan(dataBarangPenjualan: ArrayList<Barang>)
    fun deleteTempBarangPenjualan(id: Int)
}