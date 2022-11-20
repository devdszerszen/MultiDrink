package pl.dszerszen.multidrink.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import pl.dszerszen.multidrink.db.DrinksDatabase

actual class DatabaseDriverFactory {
    actual fun createDbDriver(): SqlDriver {
        return NativeSqliteDriver(DrinksDatabase.Schema, "drinks.db")
    }
}