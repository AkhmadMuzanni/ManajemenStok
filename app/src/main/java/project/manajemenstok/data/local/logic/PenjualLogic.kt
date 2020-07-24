package project.manajemenstok.data.local.logic

import android.database.Cursor
import project.manajemenstok.data.model.Penjual

interface PenjualLogic{
    fun getPenjual(): Cursor
    fun setTempPenjual(dataPenjual: String)
    fun getTempPenjual(): String
    fun createPenjual(penjual: Penjual): Int
    fun getListPenjual(): List<Penjual>
}