package hu.tb.jwt_auth.data.repository

import android.util.Log
import hu.tb.jwt_auth.data.data_source.ExampleApiSource
import hu.tb.jwt_auth.domain.repository.ExampleRepository
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(
    private val api: ExampleApiSource
) : ExampleRepository {

    override suspend fun authenticate(username: String, password: String) {
        try {
            val call = api.authenticate(username, password)
        } catch (e: Exception) {
            Log.d("MYTAG", e.message.toString())
        }

    }

    override suspend fun extendAuthentication() {
        api.extendAuthentication("")
    }
}