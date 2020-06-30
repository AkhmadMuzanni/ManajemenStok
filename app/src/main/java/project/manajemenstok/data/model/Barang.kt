package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName

data class Barang (
    @SerializedName("id")
    val id: Int=0,
    @SerializedName("namaBarang")
    val namaBarang: String="",
    @SerializedName("harga_beli")
    val hargaBeli: Int=0,
    @SerializedName("foto")
    val foto: String=""
)
