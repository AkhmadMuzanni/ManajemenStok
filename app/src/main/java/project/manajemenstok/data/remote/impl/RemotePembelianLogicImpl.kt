package project.manajemenstok.data.remote.impl

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.manajemenstok.data.model.DetailTransaksiFirebase
import project.manajemenstok.data.model.TransaksiFirebase
import project.manajemenstok.data.remote.logic.RemotePembelianLogic

class RemotePembelianLogicImpl :
    RemotePembelianLogic {
    override fun getDbReference(query: String): DatabaseReference {
        val database = Firebase.database
        return database.getReference(query)
    }

    override fun createTransaksi(transaksi: TransaksiFirebase): String {
        val dbTransaksi = getDbReference("transaksi")
        val key = dbTransaksi.push().key!!
        transaksi.uuid = key
        dbTransaksi.child(key).child(transaksi.idKlien).setValue(transaksi)
        return key
    }

    override fun createDetailTransaksi(detailTransaksi: DetailTransaksiFirebase): String {
        val dbTransaksi = getDbReference("detailTransaksi")
        val key = dbTransaksi.push().key!!
        detailTransaksi.uuid = key
        dbTransaksi.child(key).child(detailTransaksi.idTransaksi).child(detailTransaksi.idBarang).setValue(detailTransaksi)
        return key
    }

}