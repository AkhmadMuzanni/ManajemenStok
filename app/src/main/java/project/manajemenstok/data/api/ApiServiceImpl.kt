package project.manajemenstok.data.api

import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import project.manajemenstok.data.model.Barang

class ApiServiceImpl : ApiService {
    override fun getBarangs(): Single<List<Barang>> {
//        Get Live Data
//        return Rx2AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
//            .build()
//            .getObjectListSingle(Barang::class.java)
        return Rx2AndroidNetworking.get("https://tomatoleafdisease.web.app/testData.json")
            .build()
            .getObjectListSingle(Barang::class.java)

    }

}