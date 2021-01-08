package com.test.solusidigitaltest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.solusidigitaltest.model.Article
import com.test.solusidigitaltest.network.Repository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val repository: Repository,
    private val backgroundScheduler: Scheduler,
    private val mainScheduler: Scheduler
) : ViewModel() {
    private var listNews = MutableLiveData<MutableList<Article>>()
    private var newsData = MutableLiveData<Article>()

    fun setListNews() {
        compositeDisposable.add(
            repository.getNews("bitcoin","369bd90d12be44d1b0ea24f9168c06b6")
                .observeOn(backgroundScheduler)
                .subscribeOn(mainScheduler)
                .subscribe({ NewsViewModel ->
                    listNews.postValue(NewsViewModel.articles as java.util.ArrayList<Article>?)
                }, { error ->
                    println("error message " + error.message)
                    listNews.postValue(null)
                }
                )
        )
    }

    fun getListNews(): LiveData<MutableList<Article>> {
        return listNews
    }

    fun getContactById(): LiveData<Article> {
        return newsData
    }

    class ViewModelNewsFactory(
        private val compositeDisposable: CompositeDisposable,
        private val repository: Repository,
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler
    ) : ViewModelProvider.NewInstanceFactory() {
        @SuppressWarnings("unchecked")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsViewModel(
                compositeDisposable,
                repository,
                backgroundScheduler,
                mainScheduler
            ) as T
        }
    }
}