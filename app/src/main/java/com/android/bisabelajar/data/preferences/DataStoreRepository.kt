package com.android.bisabelajar.data.preferences

import androidx.datastore.core.DataStore

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun getString(key: String): String?
    suspend fun getInt(key: String): Int?
    fun getDataStore(): DataStore<*>
}