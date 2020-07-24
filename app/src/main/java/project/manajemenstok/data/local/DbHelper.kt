package project.manajemenstok.data.local

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import project.manajemenstok.data.local.logic.BarangLogic
import project.manajemenstok.data.local.logic.PembelianLogic
import project.manajemenstok.data.local.logic.PenjualLogic
import project.manajemenstok.data.local.skema.BarangSkema
import project.manajemenstok.data.local.skema.DetailPembelianSkema
import project.manajemenstok.data.local.skema.PembelianSkema
import project.manajemenstok.data.local.skema.PenjualSkema
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.model.DetailPembelian
import project.manajemenstok.data.model.Pembelian
import project.manajemenstok.data.model.Penjual

class DbHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "ManajemenStok.db", null, 1),
    BarangLogic, PenjualLogic,
    PembelianLogic {

    companion object {
        private var instance: DbHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) : DbHelper {
            if (instance == null){
                instance =
                    DbHelper(ctx.applicationContext)
            }
            return instance as DbHelper
        }
    }

    private var tempBarang : ArrayList<Barang> = ArrayList()
    private var tempDataPembelian : ArrayList<DetailPembelian> = ArrayList()
    private var tempPenjual = ""
    private var tempOngkir = 0
    private var barangUsed : ArrayList<Barang> = ArrayList()

    override fun onCreate(db: SQLiteDatabase?) {

//        Create Table

        db?.createTable("TABLE_BARANG", true,
            "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "NAMA_BARANG" to TEXT,
            "HARGA_BELI" to INTEGER,
            "FOTO" to TEXT,
            "JUMLAH" to INTEGER,
            "TOTAL" to INTEGER
        )

        db?.createTable("TABLE_PENJUAL", true,
            "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "NAMA_PENJUAL" to TEXT,
            "NO_TELP" to TEXT
        )

        db?.createTable("TABLE_PEMBELIAN", true,
            "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "ID_PENJUAL" to INTEGER,
            "TGL_PEMBELIAN" to TEXT,
            "ONGKIR" to INTEGER,
            "TOTAL_PEMBELIAN" to INTEGER,
            "METODE" to INTEGER,
            FOREIGN_KEY("ID_PENJUAL", "TABLE_PENJUAL", "ID_")
        )

        db?.createTable("TABLE_DETAIL_PEMBELIAN", true,
            "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "ID_PEMBELIAN" to INTEGER,
            "ID_BARANG" to INTEGER,
            "HARGA" to INTEGER,
            "JUMLAH" to INTEGER,
            "TOTAL" to INTEGER,
            FOREIGN_KEY("ID_PEMBELIAN", "TABLE_PEMBELIAN", "ID_"),
            FOREIGN_KEY("ID_BARANG", "TABLE_BARANG", "ID_")
        )

//        Insert Data

//        db?.insert(PenjualSkema.TABLE_PENJUAL,
//            PenjualSkema.NAMA_PENJUAL to "Penjual 1",
//            PenjualSkema.NO_TELP to "1234"
//        )
//
//        db?.insert(BarangSkema.TABLE_BARANG,
//            BarangSkema.NAMA_BARANG to "Barang Nomor 1",
//            BarangSkema.HARGA_BELI to 0,
//            BarangSkema.FOTO to "https://cdn.pixabay.com/photo/2014/04/03/10/55/t-shirt-311732_1280.png",
//            BarangSkema.JUMLAH to 0,
//            BarangSkema.TOTAL to 0
//        )
//
//        db?.insert(PembelianSkema.TABLE_PEMBELIAN,
//            PembelianSkema.ID_PENJUAL to 1,
//            PembelianSkema.TGL_PEMBELIAN to "01012001",
//            PembelianSkema.ONGKIR to 100,
//            PembelianSkema.TOTAL_PEMBELIAN to 1001,
//            PembelianSkema.METODE to 1
//        )
//        db?.insert(PembelianSkema.TABLE_PEMBELIAN,
//            PembelianSkema.ID_PENJUAL to 1,
//            PembelianSkema.TGL_PEMBELIAN to "01012001",
//            PembelianSkema.ONGKIR to 100,
//            PembelianSkema.TOTAL_PEMBELIAN to 1002,
//            PembelianSkema.METODE to 1
//        )
//        db?.insert(PembelianSkema.TABLE_PEMBELIAN,
//            PembelianSkema.ID_PENJUAL to 1,
//            PembelianSkema.TGL_PEMBELIAN to "01012001",
//            PembelianSkema.ONGKIR to 100,
//            PembelianSkema.TOTAL_PEMBELIAN to 1003,
//            PembelianSkema.METODE to 1
//        )
//
//        db?.insert(DetailPembelianSkema.TABLE_DETAIL_PEMBELIAN,
//            DetailPembelianSkema.ID_PEMBELIAN to 1,
//            DetailPembelianSkema.ID_BARANG to 1,
//            DetailPembelianSkema.HARGA to 235,
//            DetailPembelianSkema.JUMLAH to 5,
//            DetailPembelianSkema.TOTAL to 1
//        )
//
////        db?.insert(BarangSkema.TABLE_BARANG,
////            BarangSkema.NAMA_BARANG to "Barang Nomor 2",
////            BarangSkema.HARGA_BELI to 1234,
////            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
////        )
////        db?.insert(BarangSkema.TABLE_BARANG,
////            BarangSkema.NAMA_BARANG to "Barang Nomor 3",
////            BarangSkema.HARGA_BELI to 1234,
////            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
////        )
////        db?.insert(BarangSkema.TABLE_BARANG,
////            BarangSkema.NAMA_BARANG to "Barang Nomor 4",
////            BarangSkema.HARGA_BELI to 1234,
////            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
////        )
////        db?.insert(BarangSkema.TABLE_BARANG,
////            BarangSkema.NAMA_BARANG to "Barang Nomor 7",
////            BarangSkema.HARGA_BELI to 1234,
////            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
////        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("TABLE_BARANG", true)
        onCreate(db)
    }

    override fun getBarang(): Cursor {
        val db = this.readableDatabase
        val retVal = db.rawQuery("SELECT * FROM TABLE_BARANG",null)

        return retVal
    }

    override fun setBarangFromRemote(dataBarang: List<Barang>) {
        val db = this.readableDatabase
        for (barang in dataBarang) {
            db?.insert(
                BarangSkema.TABLE_BARANG,
                BarangSkema.NAMA_BARANG to barang.namaBarang,
                BarangSkema.HARGA_BELI to barang.harga,
                BarangSkema.FOTO to barang.foto,
                BarangSkema.JUMLAH to barang.jumlah,
                BarangSkema.TOTAL to barang.total
            )
        }
    }


    val Context.db : DbHelper
        get() = DbHelper.getInstance(applicationContext)

    override fun insertTempBarang(dataBarang: Barang){
        tempBarang.add(dataBarang)
    }

    override fun getTempBarang(): ArrayList<Barang> {
        return tempBarang
    }

    override fun setTempBarang(dataBarang: ArrayList<Barang>) {
        tempBarang = dataBarang
    }

    override fun createBarang(barang: Barang): Int {
        val db = this.readableDatabase
        db?.insert(
            BarangSkema.TABLE_BARANG,
            BarangSkema.NAMA_BARANG to barang.namaBarang,
            BarangSkema.HARGA_BELI to barang.harga,
            BarangSkema.FOTO to barang.foto,
            BarangSkema.JUMLAH to barang.jumlah,
            BarangSkema.TOTAL to barang.total
        )

        val lastItem = db.rawQuery("SELECT * FROM TABLE_BARANG",null)
        lastItem.moveToLast()
        return lastItem.getInt(lastItem.getColumnIndex(BarangSkema.ID))
    }

    override fun updateBarang(barang: Barang) {
        val db = this.readableDatabase
        db.update(
            BarangSkema.TABLE_BARANG,
            BarangSkema.NAMA_BARANG to barang.namaBarang,
            BarangSkema.HARGA_BELI to barang.harga,
            BarangSkema.FOTO to barang.foto,
            BarangSkema.JUMLAH to barang.jumlah,
            BarangSkema.TOTAL to barang.total
        ).whereSimple("ID_ = ?", barang.id.toString()).exec()
    }

    override fun getBarangById(id: Int): Barang {
        val db = this.readableDatabase
        val barang = db.rawQuery("SELECT * FROM TABLE_BARANG WHERE ID_ = ?", arrayOf(id.toString()))
        return barang.parseSingle(classParser())
    }

    override fun deleteTempBarang(id: Int) {
        tempBarang.removeAt(id)

    }

    override fun getBarangUsed(): ArrayList<Barang> {
        barangUsed = ArrayList()
        for (barang in tempBarang){
            if(barang.id != 0){
                barangUsed.add(barang)
            }
        }
        return barangUsed
    }

    override fun getUnusedBarang(barangUsed: ArrayList<Barang>): ArrayList<Barang> {
        val allBarang: List<Barang> = getBarang().parseList(classParser())

        var result = ArrayList<Barang>()
        for(barang in allBarang){
            var isUsed = false
            for(used in barangUsed){
                if(barang.id == used.id){
                    isUsed = true
                    break
                }
            }
            if(!isUsed){
                result.add(barang)
            }
        }

        return result
    }

    override fun getPenjual(): Cursor {
        val db = this.readableDatabase
        val retVal = db.rawQuery("SELECT * FROM TABLE_PENJUAL",null)

        return retVal
    }

    override fun setTempPenjual(dataPenjual: String) {
        tempPenjual = dataPenjual
    }

    override fun getTempPenjual(): String {
        return tempPenjual
    }

    override fun createPenjual(penjual: Penjual): Int {
        val db = this.readableDatabase
        db?.insert(
            PenjualSkema.TABLE_PENJUAL,
            PenjualSkema.NAMA_PENJUAL to penjual.namaPenjual,
            PenjualSkema.NO_TELP to penjual.noTelp
        )

        val lastItem = db.rawQuery("SELECT * FROM TABLE_PENJUAL",null)
        lastItem.moveToLast()
        return lastItem.getInt(lastItem.getColumnIndex(PenjualSkema.ID))
    }

    override fun getListPenjual(): List<Penjual> {
        val db = this.readableDatabase
        val listPenjual = db.rawQuery("SELECT * FROM TABLE_PENJUAL",null)
        return listPenjual.parseList(classParser())
    }

    override fun getPembelian(): Cursor {
        val db = this.readableDatabase
        val retVal = db.rawQuery("SELECT * FROM TABLE_PEMBELIAN",null)

        return retVal
    }

    override fun getTempDataPembelian(): ArrayList<DetailPembelian> {
        return tempDataPembelian
    }

    override fun insertTempDataPembelian(detailPembelian: DetailPembelian) {
        tempDataPembelian.add(detailPembelian)
    }

    override fun setTempDataPembelian(detailPembelian: ArrayList<DetailPembelian>) {
        tempDataPembelian = detailPembelian
    }

    override fun setTempOngkir(ongkir: Int) {
        tempOngkir = ongkir
    }

    override fun getTempOngkir(): Int {
        return tempOngkir
    }

    override fun createPembelian(pembelian: Pembelian): Int {
        val db = this.readableDatabase
        db?.insert(
            PembelianSkema.TABLE_PEMBELIAN,
            PembelianSkema.ID_PENJUAL to pembelian.idPenjual,
            PembelianSkema.TGL_PEMBELIAN to pembelian.tglPembelian,
            PembelianSkema.ONGKIR to pembelian.ongkir,
            PembelianSkema.METODE to pembelian.metode,
            PembelianSkema.TOTAL_PEMBELIAN to pembelian.totalPembelian
        )

        val lastItem = db.rawQuery("SELECT * FROM TABLE_PEMBELIAN",null)
        lastItem.moveToLast()
        return lastItem.getInt(lastItem.getColumnIndex(PembelianSkema.ID))
    }

    override fun getListPembelian(): List<Pembelian> {
        val db = this.readableDatabase
        val listPembelian = db.rawQuery("SELECT * FROM TABLE_PEMBELIAN",null)
        return listPembelian.parseList(classParser())
    }

    override fun createDetailPembelian(detailPembelian: DetailPembelian): Int {
        val db = this.readableDatabase
        db?.insert(
            DetailPembelianSkema.TABLE_DETAIL_PEMBELIAN,
            DetailPembelianSkema.ID_PEMBELIAN to detailPembelian.idPembelian,
            DetailPembelianSkema.ID_BARANG to detailPembelian.idBarang,
            DetailPembelianSkema.HARGA to detailPembelian.harga,
            DetailPembelianSkema.JUMLAH to detailPembelian.jumlah,
            DetailPembelianSkema.TOTAL to detailPembelian.total
        )

        val lastItem = db.rawQuery("SELECT * FROM TABLE_DETAIL_PEMBELIAN",null)
        lastItem.moveToLast()
        return lastItem.getInt(lastItem.getColumnIndex(DetailPembelianSkema.ID))
    }

    //    ------------------------------------=====Riwayat=====-------------------------------------

    fun getRiwayatTransaksi(): Cursor {
        val db = this.readableDatabase
        val retVal = db.rawQuery("SELECT * FROM TABLE_PEMBELIAN ",null)

        return retVal
    }

}