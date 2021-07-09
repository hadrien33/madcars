package com.example.madcars.dbaccess

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class AppDatabaseHelper(context: Context) {
    // Bloc de code "static" :
    companion object
    {
        // Helper :
        private lateinit var databaseHelper: AppDatabaseHelper

        // Getter instance :
        fun getDatabase(context: Context): AppDatabase
        {
            if (!::databaseHelper.isInitialized)
            {
                databaseHelper = AppDatabaseHelper(context)
            }
            return databaseHelper.database
        }
        // Migrations :
        val MIGRATION_1_2 = object : Migration(1, 2)
        {
            @Override
            override fun migrate(database: SupportSQLiteDatabase)
            {
            }
        }
    }

    // Base de données :
    val database: AppDatabase = Room
        .databaseBuilder(context.applicationContext, AppDatabase::class.java, "cars.db")
        .allowMainThreadQueries()
        .build()

}