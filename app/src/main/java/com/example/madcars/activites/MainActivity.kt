package com.example.madcars.activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcars.R
import com.example.madcars.adapters.VehiculeAdapter
import com.example.madcars.apicars.RetroFitSingleton
import com.example.madcars.apicars.WSInterface
import com.example.madcars.bo.Vehicule
import com.example.madcars.dbaccess.AppDatabaseHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewVehicules: RecyclerView
    private lateinit var vehiculeAdapter: VehiculeAdapter

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //switch
        val sw1 = findViewById<Switch>(R.id.switch1)

        // recyclerView :
        recyclerViewVehicules = findViewById(R.id.liste_vehicule)

        // à ajouter pour de meilleures performances :
        recyclerViewVehicules.setHasFixedSize(true)

        // layout manager, décrivant comment les items sont disposés :
        val layoutManager = LinearLayoutManager(this)
        recyclerViewVehicules.layoutManager = layoutManager
        getApi()
        sw1?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val favoris: List<Vehicule> = AppDatabaseHelper.getDatabase(this).favorisDAO()
                    .getListeFavoris()
                vehiculeAdapter = VehiculeAdapter(favoris, this@MainActivity)
                recyclerViewVehicules.adapter = vehiculeAdapter
            } else {
                getApi()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerViewVehicules.adapter?.notifyDataSetChanged()
    }

    private fun getApi(){
        val service = RetroFitSingleton.retrofit.create(WSInterface::class.java)
        val call = service.getVehicules()
        call.enqueue(object : Callback<List<Vehicule>> {
            override fun onResponse(
                call: Call<List<Vehicule>>, response: Response<List<Vehicule>>)
            {
                if (response.isSuccessful) {
                    val vehicules = response.body()
                    if (vehicules != null) {
                        // adapter :
                        vehiculeAdapter = VehiculeAdapter(vehicules, this@MainActivity)
                        recyclerViewVehicules.adapter = vehiculeAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Vehicule>>, t: Throwable) {
                Log.e("tag", "${t.message}")
            }
        })
    }
}