package com.example.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.news.database.NewsData
import com.example.news.database.SourceData
import com.example.news.service.NewsApi
import com.orhanobut.logger.Logger
import io.realm.*

class MainViewModel : BaseViewModel() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    private val mRealmLiveData = realm.where(NewsData::class.java).findAllAsync().asLiveData()
    val newsListData: LiveData<MutableList<NewsData>> =
        Transformations.map(mRealmLiveData) { realmResult ->
            realm.copyFromRealm(realmResult)
        }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

    fun getNews() {
        launch(
            block = {
                val data = NewsApi.getNews()
                realm.beginTransaction()
                realm.where(NewsData::class.java).findAll().deleteAllFromRealm()
                for (item in data) {
                    val sourceData = realm.createObject(SourceData::class.java).apply {
                        id = item.source?.id ?: ""
                        name = item.source?.name ?: ""
                    }
                   realm.createObject(NewsData::class.java).apply {
                       source = sourceData
                       author = item.author ?: ""
                       title = item.title ?: ""
                       description = item.description ?: ""
                       url = item.url ?: ""
                       urlToImage = item.urlToImage ?: ""
                       publishedAt = item.publishedAt ?: ""
                       content = item.content ?: ""
                    }
                }
                realm.commitTransaction()
            },
            error = {
                Logger.e(it.message.toString())
            },
            isLoading = {

            }
        )
    }
}

class RealmLiveData<T : RealmModel>(private val results: RealmResults<T>) :
    LiveData<RealmResults<T>>() {
    private val listener: RealmChangeListener<RealmResults<T>> =
        RealmChangeListener { results -> value = results }

    override fun onActive() {
        results.addChangeListener(listener)
    }

    override fun onInactive() {
        results.removeChangeListener(listener)
    }
}

fun <T: RealmModel> RealmResults<T>.asLiveData() = RealmLiveData(this)