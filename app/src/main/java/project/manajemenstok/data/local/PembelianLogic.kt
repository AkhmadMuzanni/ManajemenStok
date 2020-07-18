package project.manajemenstok.data.local

import android.database.Cursor

interface PembelianLogic{
    fun getPembelian(): Cursor
}