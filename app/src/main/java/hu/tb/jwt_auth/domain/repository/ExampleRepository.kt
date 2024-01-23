package hu.tb.jwt_auth.domain.repository

import hu.tb.jwt_auth.domain.model.AuthResponseDto
import hu.tb.jwt_auth.domain.util.Resource

interface ExampleRepository {

    suspend fun authenticate(username: String, password: String): Resource<AuthResponseDto>

    suspend fun extendAuthentication(token: String): Resource<AuthResponseDto>
}