package hu.tb.jwt_auth.domain.repository

interface ExampleRepository {

    suspend fun authenticate(username: String, password: String)

    suspend fun extendAuthentication()
}