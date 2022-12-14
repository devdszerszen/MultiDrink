package pl.dszerszen.multidrink.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.module
import pl.dszerszen.multidrink.db.DrinksDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDbDriver(): SqlDriver {
        return AndroidSqliteDriver(DrinksDatabase.Schema, context, "drinks.db")
    }
}

actual fun databaseModule() = module {
    single { DrinksDatabase(DatabaseDriverFactory(get()).createDbDriver()) }
}