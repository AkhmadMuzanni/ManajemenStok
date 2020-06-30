package project.manajemenstok.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getBarangs() = apiService.getBarangs()

}