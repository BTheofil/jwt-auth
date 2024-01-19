package hu.tb.jwt_auth.data.data_source

import hu.tb.jwt_auth.domain.model.AuthResponseDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ExampleApiSource {

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("/idp/api/v1/token")
    suspend fun authenticate(
        @Field ("grant_type") grant_type: String = "password",
        @Field ("client_id") client_id: String = "69bfdce9-2c9f-4a12-aa7b-4fe15e1228dc"
    ): Call<ResponseBody>


    @POST("/idp/api/v1/token")
    suspend fun extendAuthentication(
        @Body refresh_token: String,
        @Body grant_type: String = "refresh_token",
        @Body client_id: String = "69bfdce9-2c9f-4a12-aa7b-4fe15e1228dc"
    ): Response<AuthResponseDto>
}