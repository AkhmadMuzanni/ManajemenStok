package project.manajemenstok.data.local

import android.database.Cursor

interface BarangLogic{
    fun getBarang(): Cursor
}