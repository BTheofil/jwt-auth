package hu.tb.jwt_auth.domain.model

data class AuthResponseDto(
    val access_token: String,
    val refresh_token: String,
    val expires_in: Int,
    val token_type: String
)