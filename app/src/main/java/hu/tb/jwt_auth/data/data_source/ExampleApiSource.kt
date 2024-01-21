package hu.tb.jwt_auth.data.data_source

import hu.tb.jwt_auth.domain.model.AuthResponse
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface ExampleApiSource {

    @FormUrlEncoded
    @POST("/idp/api/v1/token")
    suspend fun authenticate(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password",
        @Field("client_id") clientId: String = "69bfdce9-2c9f-4a12-aa7b-4fe15e1228dc"
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("/idp/api/v1/token")
    suspend fun extendAuthentication(
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String = "69bfdce9-2c9f-4a12-aa7b-4fe15e1228dc"
    ): Response<AuthResponse>
}