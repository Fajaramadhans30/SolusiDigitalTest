package com.test.solusidigitaltest.view

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.solusidigitaltest.R
import com.test.solusidigitaltest.adapter.NewsAdapter
import com.test.solusidigitaltest.model.Article
import com.test.solusidigitaltest.network.Provider
import com.test.solusidigitaltest.viewmodel.NewsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.failed_load_layout.*
import kotlinx.android.synthetic.main.progress_loading.*
import org.jetbrains.anko.support.v4.onRefresh


class HomeActivity : AppCompatActivity() {
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel
    private var dataListNews: MutableList<Article?> = mutableListOf()
    private val compositeDisposable = CompositeDisposable()
    private val repository = Provider.newsProviderRepository()
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        idError.visibility = View.GONE

        newsAdapter = NewsAdapter(dataListNews, applicationContext) {
            val intentDetail = Intent(applicationContext, NewsDetailActivity::class.java)
            intentDetail.putExtra(getString(R.string.image), it.urlToImage)
            intentDetail.putExtra(getString(R.string.intent_title), it.title)
            intentDetail.putExtra(getString(R.string.author), it.author)
            intentDetail.putExtra(getString(R.string.publishedAt), it.publishedAt)
            intentDetail.putExtra(getString(R.string.description), it.description)
            startActivity(intentDetail)
        }

        linearLayoutManager = LinearLayoutManager(applicationContext)
        rv_news.layoutManager = linearLayoutManager
        rv_news.hasFixedSize()
        rv_news.adapter = newsAdapter

        newsViewModel = ViewModelProviders.of(
            this,
            NewsViewModel.ViewModelNewsFactory(
                compositeDisposable,
                repository,
                AndroidSchedulers.mainThread(),
                Schedulers.io()
            )
        ).get(NewsViewModel::class.java)
        newsViewModel.setListNews()
        newsViewModel.getListNews().observe(this, getNews)
        swpNews.onRefresh {
            swpNews.isRefreshing = true
            newsViewModel.setListNews()
            newsViewModel.getListNews().observe(this, getNews)
        }
    }

    private val getNews = Observer<MutableList<Article>> { newsItems ->
        if (newsItems != null) {
            Log.d("TAG", ": ARITICLEE " + newsItems.size)
            dataListNews.clear()
            rv_news.visibility = View.VISIBLE
            idLoading.visibility = View.GONE
            idError.visibility = View.GONE
            if (newsItems.size > 0) {
                val dataListNews: MutableList<Article?> = mutableListOf()
                newsItems.let { dataListNews.addAll(it) }
                newsAdapter.addData(dataListNews)
            }
        } else {
            rv_news.visibility = View.GONE
            idLoading.visibility = View.VISIBLE
            idError.visibility = View.VISIBLE
        }
        swpNews.isRefreshing = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}