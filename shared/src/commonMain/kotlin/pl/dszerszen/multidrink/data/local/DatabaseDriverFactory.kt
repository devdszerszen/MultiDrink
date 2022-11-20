package pl.dszerszen.multidrink.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDbDriver(): SqlDriver
}