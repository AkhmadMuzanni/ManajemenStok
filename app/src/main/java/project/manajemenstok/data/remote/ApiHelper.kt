package project.manajemenstok.data.remote

class ApiHelper(private val apiService: ApiService) {

    fun getBarangs() = apiService.getBarangs()

}