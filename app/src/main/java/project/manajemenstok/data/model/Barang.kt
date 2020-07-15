package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName

data class Barang (
    @SerializedName("id")
    var id: Int=0,
    @SerializedName("namaBarang")
    var namaBarang: String="",
    @SerializedName("hargaBeli")
    val hargaBeli: Int=0,
    @SerializedName("foto")
    val foto: String="",
    @SerializedName("jumlah")
    val jumlah: Int=0,
    @SerializedName("total")
    val total: Int=0

)
