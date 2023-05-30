package com.example.movieapp.data.network

import com.example.movieapp.data.network.models.Item
import com.example.movieapp.data.network.models.SearchItemCollection
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.omdbapi.com"
private const val API_KEY = "4806691f"
private val jsonConfig = Json {
    ignoreUnknownKeys = true
}

@OptIn(ExperimentalSerializationApi::class)
private val retrofit = Retrofit.Builder()
    .addConverterFactory(jsonConfig.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .client(getClient())
    .build()

fun getClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val clientConfig = OkHttpClient.Builder()
    return clientConfig
        .addNetworkInterceptor(interceptor)
        .build()
}

interface MovieApiService {
    @GET("/?apiKey=$API_KEY")
    suspend fun getMovie(
        @Query("i") movieId: String
    ): Item?

    @GET("/?apiKey=$API_KEY&page=1")
    suspend fun getItemsBySearch(
        @Query("s") searchQuery: String,
        @Query("type") itemType: String
    ): SearchItemCollection?
}

object MovieApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}
