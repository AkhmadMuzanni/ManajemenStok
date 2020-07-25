package project.manajemenstok.data.remote.logic

import com.google.firebase.database.DatabaseReference
import io.reactivex.Single
import project.manajemenstok.data.model.KlienFirebase
import project.manajemenstok.data.model.Penjual

interface RemotePenjualLogic {
    fun getPenjual(): Single<List<Penjual>>
    fun setKlien(klien: KlienFirebase): String
    fun getDbReference(query: String): DatabaseReference
}