package project.manajemenstok.data.remote.impl

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.reactivex.Single
import project.manajemenstok.data.model.KlienFirebase
import project.manajemenstok.data.model.Penjual
import project.manajemenstok.data.remote.logic.RemoteCustomerLogic

class RemoteCustomerLogicImpl :
    RemoteCustomerLogic {
    override fun getPenjual(): Single<List<Penjual>> {
        TODO("Not yet implemented")
    }

    override fun setKlien(klien: KlienFirebase): String {
        val dbKlien = getDbReference("klien")
        val key = dbKlien.push().key!!
        klien.uuid = key
        dbKlien.child(key).setValue(klien)
        return key
    }

    override fun getDbReference(query: String): DatabaseReference {
        val database = Firebase.database
        return database.getReference(query)
    }
}