package project.manajemenstok.data.local

import android.database.Cursor

interface PenjualLogic{
    fun getPenjual(): Cursor
    fun setTempPenjual(dataPenjual: String)
    fun getTempPenjual(): String
}