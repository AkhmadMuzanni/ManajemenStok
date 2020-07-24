package project.manajemenstok.data.remote.impl

import io.reactivex.Single
import project.manajemenstok.data.model.Penjual
import project.manajemenstok.data.remote.logic.RemotePenjualLogic

class RemotePenjualLogicImpl :
    RemotePenjualLogic {
    override fun getPenjual(): Single<List<Penjual>> {
        TODO("Not yet implemented")
    }
}