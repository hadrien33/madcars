package com.example.madcars.dbaccess

import androidx.room.*
import com.example.madcars.bo.Vehicule

@Dao
abstract class FavorisDAO {
    @Query("SELECT * FROM favoris")
    abstract fun getListeFavoris(): List<Vehicule>

    @Query("SELECT * FROM favoris WHERE id = :id ")
    abstract fun findById(id: Int?): List<Vehicule>

    @Insert
    abstract fun insert(vararg vehicule: Vehicule)

    @Update
    abstract fun update(vararg vehicule: Vehicule)

    @Delete
    abstract fun delete(vararg vehicule: Vehicule)
}