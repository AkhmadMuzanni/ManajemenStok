package project.manajemenstok.data.local

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class BarangDbHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "ManajemenStok.db", null, 1),
    BarangLogic {

    companion object {
        private var instance: BarangDbHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) : BarangDbHelper {
            if (instance == null){
                instance =
                    BarangDbHelper(ctx.applicationContext)
            }
            return instance as BarangDbHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {

//        Create Table

        db?.createTable("TABLE_BARANG", true,
            "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "NAMA_BARANG" to TEXT,
            "HARGA_BELI" to INTEGER,
            "FOTO" to TEXT)

//        Insert Data

        db?.insert(
            BarangSkema.TABLE_BARANG,
            BarangSkema.ID to 1,
            BarangSkema.NAMA_BARANG to "Barang Nomor 1",
            BarangSkema.HARGA_BELI to 1234,
            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
        )
        db?.insert(
            BarangSkema.TABLE_BARANG,
            BarangSkema.ID to 2,
            BarangSkema.NAMA_BARANG to "Barang Nomor 2",
            BarangSkema.HARGA_BELI to 1234,
            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
        )
        db?.insert(
            BarangSkema.TABLE_BARANG,
            BarangSkema.ID to 3,
            BarangSkema.NAMA_BARANG to "Barang Nomor 3",
            BarangSkema.HARGA_BELI to 1234,
            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
        )
        db?.insert(
            BarangSkema.TABLE_BARANG,
            BarangSkema.ID to 4,
            BarangSkema.NAMA_BARANG to "Barang Nomor 4",
            BarangSkema.HARGA_BELI to 1234,
            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
        )
        db?.insert(
            BarangSkema.TABLE_BARANG,
            BarangSkema.ID to 5,
            BarangSkema.NAMA_BARANG to "Barang Nomor 5",
            BarangSkema.HARGA_BELI to 1234,
            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("TABLE_BARANG", true)
        onCreate(db)
    }

    //    fun getBarang(): Cursor {
//        val db = this.readableDatabase
//        val retVal = db.rawQuery("SELECT * FROM TABLE_BARANG",null)
//
//        return retVal

//    }

    override fun getBarang(): Cursor {
        val db = this.readableDatabase
        val retVal = db.rawQuery("SELECT * FROM TABLE_BARANG",null)

        return retVal
    }

    val Context.db : BarangDbHelper
        get() = getInstance(
            applicationContext
        )


}