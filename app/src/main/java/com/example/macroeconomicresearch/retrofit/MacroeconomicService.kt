package com.example.macroeconomicresearch.retrofit

import com.example.macroeconomicresearch.retrofit.response.Root2
import com.example.macroeconomicresearch.retrofit.response.Root3
import com.squareup.moshi.Moshi
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import javax.crypto.Mac

interface MacroeconomicService {

    @GET("{countries}/indicator/{loadType}?format=json&per_page=1000")
    fun getMacroeconomic(@Path("loadType") loadType: String, @Path("countries")countries : String, @Query("date")date : String): Call<List<Any>>




    companion object {
        private const val BASE_URL = "https://api.worldbank.org/v2/country/"

        val service : MacroeconomicService by lazy {

             Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getBaseHttpClient().build())
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
                .build()
                .create()
        }

        fun getBaseHttpClient() : OkHttpClient.Builder{
            return OkHttpClient.Builder()
                .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
                .retryOnConnectionFailure(true)
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
        }
    }
}