package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.impl.RemoteKategoriLogicImpl
import project.manajemenstok.data.repository.KategoriRepository
import project.manajemenstok.utils.Resource

class KategoriViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val kategoriRepository = KategoriRepository(
        RemoteKategoriLogicImpl()
    )

    fun fetchKategori(){
        kategoriRepository.fetchKategori()
    }

    fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>> {
        return kategoriRepository.getKategori()
    }
}