package com.djt.testapplication

import android.content.Context
import android.content.SharedPreferences

object Constants {

    val cartlist = ""
    fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences("Data", Context.MODE_PRIVATE)//table name
    }
}