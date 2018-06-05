@file:JvmName("NetworkModule")

package com.swarawan.corelibrary.network

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import com.swarawan.corelibrary.R
import com.swarawan.corelibrary.extensions.clazz
import com.swarawan.corelibrary.utils.TextUtils
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.*
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by rioswarawan on 2/23/18.
 */
@Module
class NetworkModule {

    companion object {
        val CACHE_DIR_SIZE_30MB = 30 * 1024 * 1024
        val KEEP_ALIVE_DURATION = (30 * 1000).toLong()
        val MAX_IDLE_CONNECTIONS = 10
    }

    @Provides
    @Singleton
    fun providesNetworkConfig(context: Context): NetworkConfig {
        return NetworkConfig(context.getString(R.string.server_url))
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(clazz<Date>(), DateTypeAdapter()).create()
    }

    @Provides
    @Singleton
    fun providesCache(context: Context): Cache =
            Cache(context.externalCacheDir ?: context.cacheDir, CACHE_DIR_SIZE_30MB.toLong())

    @Provides
    @Singleton
    fun providesConnectionPool(): ConnectionPool =
            ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MILLISECONDS)

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                             connectionPool: ConnectionPool,
                             cache: Cache): OkHttpClient {
        val timeout = 20
        return OkHttpClient.Builder()
                .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .connectionPool(connectionPool)
                .build()
    }

    @Provides
    @Singleton
    fun providesHttpLogginInterceptor(context: Context): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = getLevel(context.getString(R.string.okhttp_log_level))
        return interceptor
    }

    @Provides
    @Singleton
    fun providesRetrofit(config: NetworkConfig,
                         okHttpClient: OkHttpClient,
                         gson: Gson): Retrofit =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(config.baseUrl)
                    .build()

    @Provides
    @Singleton
    fun providesNetworkUtils(context: Context): NetworkUtil =
            NetworkUtil(context)

    private fun getLevel(level: String?): HttpLoggingInterceptor.Level =
            when (level) {
                "NONE" -> HttpLoggingInterceptor.Level.NONE
                "BASIC" -> HttpLoggingInterceptor.Level.BASIC
                "HEADERS" -> HttpLoggingInterceptor.Level.HEADERS
                "BODY" -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
}