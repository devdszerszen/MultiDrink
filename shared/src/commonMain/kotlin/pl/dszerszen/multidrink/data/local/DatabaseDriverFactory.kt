package pl.dszerszen.multidrink.data.local

import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module

expect class DatabaseDriverFactory {
    fun createDbDriver(): SqlDriver
}

expect fun databaseModule(): Module