package project.manajemenstok.data.remote

import io.reactivex.Single
import project.manajemenstok.data.model.Barang

interface RemoteBrangLogic {
    fun getBarangs(): Single<List<Barang>>
}