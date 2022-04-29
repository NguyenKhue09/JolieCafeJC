package com.khue.joliecafejp.data.repository

import com.khue.joliecafejp.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.khue.joliecafejp.utils.Constants.Companion.PREFERENCES_NAME
import com.khue.joliecafejp.utils.Constants.Companion.PREFERENCES_USER_TOKEN
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private object PreferenceKeys {
        val userToken = stringPreferencesKey(PREFERENCES_USER_TOKEN)
    }

    private val dataStore = context.dataStore

    override suspend fun saveUserToken(userToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.userToken] = userToken
        }
    }

    override fun readUserToken(): Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw  exception
            }
        }
        .map { preferences ->
            val userToken = preferences[PreferenceKeys.userToken] ?: ""
            userToken
        }
}