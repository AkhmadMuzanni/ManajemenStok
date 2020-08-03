package project.manajemenstok.utils

import java.text.NumberFormat
import java.util.*

class Helper {
    companion object {
        fun getAngka(string: String): Int {
            val idLocale = Locale("id", "ID")
            val nf = NumberFormat.getNumberInstance(idLocale)
            return nf.parse(string).toInt()
        }

        fun getFormat(int: Int): String {
            val idLocale = Locale("id", "ID")
            val nf = NumberFormat.getNumberInstance(idLocale)
            return nf.format(int)
        }
    }
}