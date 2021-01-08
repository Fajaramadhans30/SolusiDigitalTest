package com.test.solusidigitaltest.network

import com.test.solusidigitaltest.model.NewsModel
import io.reactivex.Observable

class Repository(private val apiService: ApiService) {
    fun getNews(q:String, apikey:String): Observable<NewsModel> {
        return apiService.getNews(q, apikey)
    }
}