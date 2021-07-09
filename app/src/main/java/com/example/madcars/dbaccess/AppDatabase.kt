package com.example.madcars.dbaccess

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.madcars.bo.Vehicule

@Database(entities = [Vehicule::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun favorisDAO(): FavorisDAO
}