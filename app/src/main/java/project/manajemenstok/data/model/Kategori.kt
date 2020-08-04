package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Kategori (
    @SerializedName("uuid")
    var uuid: String="",
    @SerializedName("nama")
    var nama: String="",
    @SerializedName("foto")
    var foto: String="",
    @SerializedName("jumlah")
    var jumlah: Int=0
): Serializable
