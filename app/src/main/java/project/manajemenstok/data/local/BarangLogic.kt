package project.manajemenstok.data.local

import android.database.Cursor
import project.manajemenstok.data.model.Barang

interface BarangLogic{
    fun getBarang(): Cursor
    fun setBarangFromRemote(dataBarang: List<Barang>)
    fun insertTempBarang(dataBarang: Barang)
    fun getTempBarang(): ArrayList<Barang>
}