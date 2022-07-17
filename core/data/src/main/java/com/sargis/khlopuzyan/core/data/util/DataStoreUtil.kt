package com.sargis.khlopuzyan.core.data.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sargis.khlopuzyan.core.domain.constant.Constants
import kotlinx.coroutines.flow.first

class DataStoreUtil(context: Context) {

    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(Constants.DataStore.OVERPLAY_PREFERENCES)
    private val dataStore: DataStore<Preferences> = context._dataStore

    suspend fun saveLongInDataStore(key: String, value: Long) {
        val dataStoreKey = longPreferencesKey(key)
        dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    suspend fun readLongFromDataStore(key: String): Long? {
        val dataStoreKey = longPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun saveIntInDataStore(key: String, value: Int) {
        val dataStoreKey = intPreferencesKey(key)
        dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    suspend fun readIntFromDataStore(key: String): Int? {
        val dataStoreKey = intPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

}