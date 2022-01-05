package com.example.news.database

import io.realm.RealmObject

open class NewsData(
    var source: SourceData? = null,
    var author: String = "",
    var title: String = "",
    var description: String = "",
    var url: String = "",
    var urlToImage: String = "",
    var publishedAt: String = "",
    var content: String = ""
): RealmObject()

open class SourceData(
    var id: String = "",
    var name: String = ""
): RealmObject()

