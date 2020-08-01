package project.manajemenstok.utils

class Constants {
    class JenisTransaksiValue{
        companion object {
            val PEMBELIAN = 0
            val PENJUALAN = 1
        }
    }

    class MetodePembayaran{
        companion object {
            val CASH = 0
            val DEBET = 1
            val KREDIT = 2
        }
    }

    class RequestCodeIntent{
        companion object {
            val INPUT_BARANG = 1
            val KONFIRMASI_TRANSAKSI = 2
            val DETAIL_BARANG = 2
        }
    }
}