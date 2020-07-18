package project.manajemenstok.data.local

data class PembelianSkema(val id:Long?, val idPenjual: Int, val tglPembelian: String, val totalPembelian: Int) {
    companion object {
        const val TABLE_PEMBELIAN : String = "TABLE_PEMBELIAN"
        const val ID: String = "ID_"
        const val ID_PENJUAL: String = "ID_PENJUAL"
        const val TGL_PEMBELIAN: String = "TGL_PEMBELIAN"
        const val TOTAL_PEMBELIAN: String = "TOTAL_PEMBELIAN"
    }


}