package com.sargis.khlopuzyan.overplay

import android.app.Application
import com.sargis.khlopuzyan.connector.session.sessionModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class OverplayApp : Application() {

    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OverplayApp)
            modules(
                listOf(
                    sessionModule(),
                )
            )
        }
    }

}