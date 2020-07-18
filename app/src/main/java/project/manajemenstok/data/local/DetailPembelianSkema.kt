package project.manajemenstok.data.local

data class DetailPembelianSkema(val idPembelian:Long?, val idBarang: Int, val harga: Int, val ongkir: Int, val jumlah: Int, val metode: Int) {
    companion object {
        const val TABLE_DETAIL_PEMBELIAN : String = "TABLE_DETAIL_PEMBELIAN"
        const val ID_PEMBELIAN: String = "ID_PEMBELIAN"
        const val ID_BARANG: String = "ID_BARANG"
        const val HARGA: String = "HARGA"
        const val ONGKIR: String = "ONGKIR"
        const val JUMLAH: String = "JUMLAH"
        const val METODE: String = "METODE"
    }


}