package com.test.solusidigitaltest.network

import com.test.solusidigitaltest.commons.API_URL
import com.test.solusidigitaltest.model.NewsModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    fun getNews(@Query("q") news: String, @Query("apiKey") apikey: String): Observable<NewsModel>

    companion object Factory {
        fun create(): ApiService {

            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }

            val httpClient = OkHttpClient.Builder()
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URL)
                .client(httpClient.build())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}