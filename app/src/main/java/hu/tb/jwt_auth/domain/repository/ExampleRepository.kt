package hu.tb.jwt_auth.domain.repository

import hu.tb.jwt_auth.domain.model.AuthResponse
import hu.tb.jwt_auth.domain.util.Resource

interface ExampleRepository {

    suspend fun authenticate(username: String, password: String): Resource<AuthResponse>

    suspend fun extendAuthentication()
}