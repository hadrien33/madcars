package com.example.madcars.bo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favoris")
data class Vehicule(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nom: String,
    val image: String,
    val disponible: Int,
    val prixjournalierbase: Int,
    val promotion: Int,
    val agemini: Int,
    val categorieco2: String
): Parcelable
