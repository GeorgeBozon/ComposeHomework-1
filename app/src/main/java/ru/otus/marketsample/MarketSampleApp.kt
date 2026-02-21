package ru.otus.marketsample

import android.app.Application
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.di.DaggerAppComponent

class MarketSampleApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

fun Application.getAppComponent() = (this as? MarketSampleApp)?.appComponent
