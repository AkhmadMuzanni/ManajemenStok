package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Barang (
    @SerializedName("id")
    var id: Int=0,
    @SerializedName("namaBarang")
    var namaBarang: String="",
    @SerializedName("hargaBeli")
    var harga: Int=0,
    @SerializedName("foto")
    var foto: String="",
    @SerializedName("jumlah")
    var jumlah: Int=0,
    @SerializedName("total")
    var total: Int=0,
    @SerializedName("uuid")
    var uuid: String="",
    @SerializedName("maxQuantity")
    var maxQuantity: Int=0,
    @SerializedName("isDeleted")
    var isDeleted: Int=0,
    @SerializedName("kategori")
    var kategori: String=""

): Serializable
