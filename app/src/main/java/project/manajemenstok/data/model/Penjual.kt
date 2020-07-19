package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName

data class Penjual (
    @SerializedName("id")
    var id: Int=0,
    @SerializedName("namaPenjual")
    var namaPenjual: String="",
    @SerializedName("noTelp")
    var noTelp: String=""
)
