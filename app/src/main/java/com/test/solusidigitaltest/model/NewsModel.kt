package com.test.solusidigitaltest.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class NewsModel {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null

    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = null
}