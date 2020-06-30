package project.manajemenstok.data.repository

import io.reactivex.Single
import project.manajemenstok.data.api.ApiHelper
import project.manajemenstok.data.model.Barang

class MainRepository(private val apiHelper: ApiHelper) {

    fun getBarangs(): Single<List<Barang>>{
        return apiHelper.getBarangs()
    }

}