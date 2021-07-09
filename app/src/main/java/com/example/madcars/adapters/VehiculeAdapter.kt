package com.example.madcars.adapters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.madcars.DetailFragment
import com.example.madcars.EXTRA_VEHICULE
import com.example.madcars.R
import com.example.madcars.activites.DetailActivity
import com.example.madcars.activites.EXTRA_VEHICULE_DETAIL
import com.example.madcars.activites.MainActivity
import com.example.madcars.bo.Vehicule
import com.squareup.picasso.Picasso

class VehiculeAdapter (private var listeVehicule: List<Vehicule>, private val mainActivity: MainActivity):
    RecyclerView.Adapter<VehiculeAdapter.VehiculeViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculeViewHolder
    {
        val viewVehicule = LayoutInflater.from(parent.context)
            .inflate(R.layout.vehicule_view, parent, false)
        return VehiculeViewHolder(viewVehicule)
    }

    override fun onBindViewHolder(holder: VehiculeViewHolder, position: Int)
    {
        return holder.bind(listeVehicule[position])
    }

    override fun getItemCount(): Int = listeVehicule.size

    inner class VehiculeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val textViewNomVehicle: TextView = itemView.findViewById(R.id.nom)
        private val textViewPrixVehicle: TextView = itemView.findViewById(R.id.prix)
        private val textViewCategorieVehicle: TextView = itemView.findViewById(R.id.categorie)
        private val img: ImageView = itemView.findViewById(R.id.portrait)
        fun bind(vehicule: Vehicule) {
                textViewNomVehicle.text = vehicule.nom
                textViewPrixVehicle.text = vehicule.prixjournalierbase.toString().plus(" € / jour")
                textViewCategorieVehicle.text = "Catégorie CO2 : ".plus(vehicule.categorieco2)

                Picasso.get()
                    .load("http://s519716619.onlinehome.fr/exchange/madrental/images/".plus(vehicule.image))
                    .into(img)
        }

        init
        {
            itemView.setOnClickListener {
                val vehicule = listeVehicule[adapterPosition]
                if (mainActivity.findViewById<FrameLayout>(R.id.conteneur_fragment) != null)
                {
                    // tablette :
                    val fragment = DetailFragment()

                    // paramètres vers le fragment :
                    val bundle = Bundle()
                    bundle.putParcelable(EXTRA_VEHICULE, vehicule)
                    fragment.arguments = bundle

                    // transaction :
                    val transaction: FragmentTransaction = mainActivity.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.conteneur_fragment, fragment, "conteneur_fragment")
                    transaction.commit()
                }
                else
                {
                    // smartphone :
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(EXTRA_VEHICULE_DETAIL, vehicule)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}