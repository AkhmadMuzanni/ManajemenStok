package project.manajemenstok.data.local

data class PenjualSkema(val id:Long?, val namaPenjual: String, val noTelp: String) {
    companion object {
        const val TABLE_PENJUAL : String = "TABLE_PENJUAL"
        const val ID: String = "ID_"
        const val NAMA_PENJUAL: String = "NAMA_PENJUAL"
        const val NO_TELP: String = "NO_TELP"
    }


}