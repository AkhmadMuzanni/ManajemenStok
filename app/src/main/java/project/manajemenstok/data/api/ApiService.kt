package project.manajemenstok.data.api

import io.reactivex.Single
import project.manajemenstok.data.model.Barang

interface ApiService {
    fun getBarangs(): Single<List<Barang>>
}