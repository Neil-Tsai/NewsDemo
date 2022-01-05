package com.example.news

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("user-db")
            .schemaVersion(1)
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)

    }
}