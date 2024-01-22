package hu.tb.jwt_auth.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.tb.jwt_auth.data.PreferenceKeys.PASSWORD
import hu.tb.jwt_auth.data.PreferenceKeys.USERNAME
import hu.tb.jwt_auth.domain.model.Login
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

    suspend fun setUsername(username: String) {
        settingsDataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    suspend fun setPassword(username: String) {
        settingsDataStore.edit { preferences ->
            preferences[PASSWORD] = username
        }
    }

    val getUser: Flow<Login> = settingsDataStore.data.map { preferences ->
        Login(
            username = preferences[USERNAME] ?: "",
            password = preferences[PASSWORD] ?: ""
        )
    }
}

object PreferenceKeys {
    val USERNAME = stringPreferencesKey("username")
    val PASSWORD = stringPreferencesKey("password")
}