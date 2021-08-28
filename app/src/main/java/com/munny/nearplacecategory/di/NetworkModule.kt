package com.munny.nearplacecategory.di

import com.munny.nearplacecategory.api.PoiSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providePoiSearchApi(): PoiSearchApi {
        return getRetrofit().create(PoiSearchApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PoiSearchApi.URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor {
                val newRequest = it.request().newBuilder()
                    .addHeader("X-Naver-Client-Id", CLIENT_ID_V1)
                    .addHeader("X-Naver-Client-Secret", CLIENT_SECRET_V1)
                    .build()

                it.proceed(newRequest)
            }
            .build()
    }

    companion object {
        private const val CLIENT_ID_V2 = "emzppx9dn2"
        private const val CLIENT_SECRET_V2 = "elfloCXeNXYxethM8sCTe2JkwhbgUgNsMFwiv2rg"

        private const val CLIENT_ID_V1 = "qARRa6KY7uxFBhG905Sr"
        private const val CLIENT_SECRET_V1 = "0fBpNmMXXH"
    }
}