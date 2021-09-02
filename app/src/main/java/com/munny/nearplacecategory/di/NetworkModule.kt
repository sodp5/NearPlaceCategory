package com.munny.nearplacecategory.di

import com.munny.nearplacecategory.api.KakaoLocalApi
import com.munny.nearplacecategory.base.ApiAuth
import com.munny.nearplacecategory.api.NaverSearchApi
import com.munny.nearplacecategory.api.NaverCloudPlatformApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideKakaoLocalApi(): KakaoLocalApi {
        val auth = KakaoLocalApi.KakaoLocalAuth()

        return getRetrofit(auth).create()
    }

    @Singleton
    @Provides
    fun provideNaverSearchApi(): NaverSearchApi {
        val auth = NaverSearchApi.NaverSearchAuth()

        return getRetrofit(auth).create(NaverSearchApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNaverCloudPlatformApi(): NaverCloudPlatformApi {
        val auth = NaverCloudPlatformApi.NaverCloudPlatformAuth()

        return getRetrofit(auth).create(NaverCloudPlatformApi::class.java)
    }

    private fun getRetrofit(auth: ApiAuth): Retrofit {
        val url = auth.getApiUrl()
        val headers = auth.getHeaders()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(provideOkHttpClient(headers))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(headers: Map<String, Any>): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor {
                val newRequest = it.request().newBuilder()
                    .apply {
                        headers.iterator().forEach { entry ->
                            Timber.d("key: ${entry.key}, value: ${entry.value}")
                            addHeader(entry.key, entry.value.toString())
                        }
                    }
                    .build()

                it.proceed(newRequest)
            }
            .build()
    }
}