package com.receipesta.receipefinderapp.application

import android.app.Application
import com.receipesta.receipefinderapp.database.RecipeRoomDatabase

/*
* İt is lazyly creates the database when it is needed
* İt is lazly because until it is necessary there is no need to load the database
 */
class RecipeApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database: RecipeRoomDatabase by lazy { RecipeRoomDatabase.getDatabase(this) }
}