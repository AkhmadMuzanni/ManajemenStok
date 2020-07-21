package project.manajemenstok.data.remote

import io.reactivex.Single
import project.manajemenstok.data.model.Penjual

interface RemotePenjualLogic {
    fun getPenjual(): Single<List<Penjual>>
}