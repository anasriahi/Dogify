package com.riahi.dogify.database

import com.riahi.dogify.db.DogifyDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

internal actual fun Scope.createDriver(databaseName: String): SqlDriver =
    AndroidSqliteDriver(DogifyDatabase.Schema, androidContext(), databaseName)