package project.manajemenstok.data.remote.logic

import com.google.firebase.database.DatabaseReference
import project.manajemenstok.data.model.DetailTransaksiFirebase
import project.manajemenstok.data.model.TransaksiFirebase

interface RemotePembelianLogic {
    fun getDbReference(query: String): DatabaseReference
    fun createTransaksi(transaksi: TransaksiFirebase): String
    fun createDetailTransaksi(detailTransaksi: DetailTransaksiFirebase): String
}