package project.manajemenstok.data.remote.logic

import io.reactivex.Single
import project.manajemenstok.data.model.KlienFirebase
import project.manajemenstok.data.model.Penjual

interface RemoteCustomerLogic {
    fun getPenjual(): Single<List<Penjual>>
    fun setKlien(klien: KlienFirebase): String
}