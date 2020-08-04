package project.manajemenstok.data.repository

import androidx.lifecycle.MutableLiveData
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.logic.RemoteKategoriLogic
import project.manajemenstok.utils.Resource

class KategoriRepository(private val remoteKategori: RemoteKategoriLogic) {

    fun fetchKategori(){
        remoteKategori.fetchKategori()
    }

    fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>> {
        return remoteKategori.getKategori()
    }

}