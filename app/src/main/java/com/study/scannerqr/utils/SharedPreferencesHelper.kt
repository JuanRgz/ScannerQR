package com.study.scannerqr.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    companion object{
        private const val NAME_PREFERENCES = "SCANNER"
        private const val BARCODE_SCANNED = "BARCODE_SCANNED"

        private var instance: SharedPreferencesHelper? = null

        @JvmStatic
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: SharedPreferencesHelper(context).also { instance = it }
        }
    }

    private var sp: SharedPreferences = context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE)

    fun setBarcodeScanned(barcode: String){
        sp.edit().putString(BARCODE_SCANNED, barcode).apply()
    }

    fun getBarcodeScanned(): String {
        return sp.getString(BARCODE_SCANNED, "") ?: ""
    }
}