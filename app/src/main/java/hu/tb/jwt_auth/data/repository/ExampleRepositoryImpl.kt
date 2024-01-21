package hu.tb.jwt_auth.data.repository

import android.util.Log
import hu.tb.jwt_auth.data.data_source.ExampleApiSource
import hu.tb.jwt_auth.domain.model.AuthResponse
import hu.tb.jwt_auth.domain.repository.ExampleRepository
import hu.tb.jwt_auth.domain.util.Resource
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(
    private val api: ExampleApiSource
) : ExampleRepository {

    override suspend fun authenticate(
        username: String,
        password: String
    ): Resource<AuthResponse> {
        return try {
            val result = api.authenticate(username = username, password = password)

            when (result.code()) {
                401 -> Resource.Error(
                    message = "Invalid username or password.",
                    data = null
                )

                200 -> Resource.Success(
                    data = AuthResponse(
                        accessToken = result.body()!!.accessToken,
                        tokenType = result.body()!!.tokenType,
                        expiresIn = result.body()!!.expiresIn,
                        refreshToken = result.body()!!.refreshToken
                    )
                )

                else ->
                    Resource.Error(
                        message = "Unexpected error",
                        data = null
                    )

            }

        } catch (e: Exception) {
            Log.e("Repository Auth Call", e.message.toString())
            Resource.Error(
                message = "Unexpected error",
            )
        }
    }


    override suspend fun extendAuthentication() {
        api.extendAuthentication("")
    }
}