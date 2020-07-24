package project.manajemenstok.data.local.logic

import android.database.Cursor
import project.manajemenstok.data.model.Barang

interface BarangLogic{
    fun getBarang(): Cursor
    fun setBarangFromRemote(dataBarang: List<Barang>)
    fun insertTempBarang(dataBarang: Barang)
    fun getTempBarang(): ArrayList<Barang>
    fun setTempBarang(dataBarang: ArrayList<Barang>)
    fun createBarang(barang: Barang): Int
    fun updateBarang(barang: Barang)
    fun getBarangById(id: Int): Barang
    fun deleteTempBarang(id: Int)
    fun getBarangUsed(): ArrayList<Barang>
    fun getUnusedBarang(barangUsed: ArrayList<Barang>): ArrayList<Barang>
}