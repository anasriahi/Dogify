package com.riahi.dogify.database

import com.riahi.dogify.db.DogifyDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.scope.Scope

internal actual fun Scope.createDriver(databaseName: String): SqlDriver =
    NativeSqliteDriver(DogifyDatabase.Schema, databaseName)