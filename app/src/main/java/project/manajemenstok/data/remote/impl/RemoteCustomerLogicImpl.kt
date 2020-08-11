package project.manajemenstok.data.remote.impl

import io.reactivex.Single
import project.manajemenstok.data.model.KlienFirebase
import project.manajemenstok.data.model.Penjual
import project.manajemenstok.data.remote.logic.RemoteCustomerLogic
import project.manajemenstok.utils.Helper.Companion.getDbReference

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
}