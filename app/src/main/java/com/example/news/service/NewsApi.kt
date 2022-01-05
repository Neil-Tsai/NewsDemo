package com.example.news.service

import com.example.news.App
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.neil.network.BuildConfig
import com.neil.network.NetworkClient
import com.neil.network.retryFactory.Retry
import com.orhanobut.logger.Logger
import io.realm.RealmObject
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.Exception

object NewsApi {

    object Api {
        val retrofit: ApiService by lazy {
            NetworkClient.getInstance().also {
                it.setCookie(App.instance)
                it.setLoggingInterceptor(BuildConfig.DEBUG)
            }.create(ApiService::class.java)
        }
    }

    interface ApiService {

        @GET
        @Retry(2)
        suspend fun getNews(
            @Url apiUrl: String = NEWS_URL
        ): Response<NewsResponseModel>
    }

    private const val NEWS_URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=b7c80662c4fe417d8e1b96a5707346ac"

    suspend fun getNews(): List<NewsModel> {
        val response = Api.retrofit.getNews()
        if (response.isSuccessful) {
            val responseBody = response.body()
            Logger.json(Gson().toJson(responseBody))
            return responseBody?.articles!!
        } else {
            throw Exception(response.message())
        }
    }
}