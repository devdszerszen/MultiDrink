package pl.dszerszen.multidrink.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import pl.dszerszen.multidrink.di.initKoin

class DrinksApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@DrinksApp)
            androidLogger(Level.DEBUG)
        }
    }
}