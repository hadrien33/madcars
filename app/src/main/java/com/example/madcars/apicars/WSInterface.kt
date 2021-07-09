package com.example.madcars.apicars

import com.example.madcars.bo.Vehicule
import retrofit2.Call
import retrofit2.http.GET

interface WSInterface {
    // appel get :
    @GET("get-vehicules.php")
    fun getVehicules(): Call<List<Vehicule>>;
}