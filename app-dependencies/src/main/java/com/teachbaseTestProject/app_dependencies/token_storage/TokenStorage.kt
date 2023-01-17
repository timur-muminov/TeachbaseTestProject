package com.teachbaseTestProject.app_dependencies.token_storage

import android.content.Context
import android.content.SharedPreferences

class TokenStorage(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("Tokens", Context.MODE_PRIVATE)
    private val key = "token"

    fun saveToken(token: String?) {
        token?.let {
            sharedPreferences.edit().putString(key, it).apply()
        }
    }

    fun removeToken() {
        sharedPreferences.edit()
            .remove(key)
            .apply()
    }

    fun getToken() : String = sharedPreferences.getString(key, "").toString()


    fun hasToken(): Boolean {
        return sharedPreferences.contains(key)
    }
}