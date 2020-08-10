package project.manajemenstok.data.repository


import project.manajemenstok.data.model.Akun
import project.manajemenstok.data.remote.logic.RemoteAkunLogic


class AuthRepository(private val remoteAkun: RemoteAkunLogic) {

    fun createAkun(akun: Akun): String {
        return remoteAkun.createAkun(akun)
    }

}