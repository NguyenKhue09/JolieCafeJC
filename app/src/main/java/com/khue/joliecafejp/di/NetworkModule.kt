package com.khue.joliecafejp.di

import com.khue.joliecafejp.data.remote.JolieCafeApi
import com.khue.joliecafejp.data.repository.RemoteDataSourceImpl
import com.khue.joliecafejp.domain.repository.RemoteDataSource
import com.khue.joliecafejp.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS) // connect timeout
            .writeTimeout(20, TimeUnit.SECONDS) // write timeout
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): JolieCafeApi {
        return retrofit.create(JolieCafeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        jolieCafeApi: JolieCafeApi
    ): RemoteDataSource {
        return RemoteDataSourceImpl(jolieCafeApi = jolieCafeApi)
    }

}