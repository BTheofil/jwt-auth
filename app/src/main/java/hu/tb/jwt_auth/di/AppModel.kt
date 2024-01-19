package hu.tb.jwt_auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tb.jwt_auth.data.data_source.ExampleApiSource
import hu.tb.jwt_auth.data.repository.ExampleRepositoryImpl
import hu.tb.jwt_auth.domain.repository.ExampleRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModel {

    @Provides
    @Singleton
    fun provideRetrofitApi(): ExampleApiSource =
        Retrofit.Builder()
            .baseUrl("https://example.vividmindsoft.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()

    @Provides
    @Singleton
    fun provideExampleRepository(api: ExampleApiSource): ExampleRepository =
        ExampleRepositoryImpl(api = api)

}