package hu.tb.jwt_auth.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.tb.jwt_auth.data.PreferenceKeys.REFRESH_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("credentials")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext appContext: Context
) {
    private val settingsDataStore = appContext.dataStore

    suspend fun saveToken(token: String) {
        settingsDataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = token
        }
    }

    suspend fun clearToken() {
        settingsDataStore.edit { preferences ->
            preferences.remove(REFRESH_TOKEN)
        }
    }

    val getToken: Flow<String?> = settingsDataStore.data.map { preferences ->
        preferences[REFRESH_TOKEN]
    }
}

object PreferenceKeys {
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
}