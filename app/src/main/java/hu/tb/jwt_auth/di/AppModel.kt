package hu.tb.jwt_auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tb.jwt_auth.data.data_source.ExampleApiSource
import hu.tb.jwt_auth.data.repository.ExampleRepositoryImpl
import hu.tb.jwt_auth.domain.repository.ExampleRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

const val BASE_URL = "https://example.vividmindsoft.com"

@Module
@InstallIn(SingletonComponent::class)
object AppModel {

    @Provides
    @Singleton
    fun provideRetrofitApi(): ExampleApiSource {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideExampleRepository(api: ExampleApiSource): ExampleRepository =
        ExampleRepositoryImpl(api = api)

}