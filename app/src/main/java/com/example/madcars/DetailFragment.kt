package com.example.madcars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.madcars.bo.Vehicule
import com.example.madcars.dbaccess.AppDatabaseHelper
import com.squareup.picasso.Picasso

const val EXTRA_VEHICULE = "vehicule"

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val arguments = requireArguments()
        val vehicule: Vehicule? = arguments.getParcelable(EXTRA_VEHICULE)
        val nom: TextView = view.findViewById(R.id.v_nom)
        val dispo: TextView = view.findViewById(R.id.v_dispo)
        val prix: TextView = view.findViewById(R.id.v_prix)
        val agemini: TextView = view.findViewById(R.id.v_agemini)
        val categorie: TextView = view.findViewById(R.id.v_categorie)
        val promo: TextView = view.findViewById(R.id.v_promo)
        val btn: Button = view.findViewById(R.id.btn_favoris)
        nom.text = vehicule?.nom
        dispo.text = vehicule?.disponible.toString()
        prix.text = vehicule?.prixjournalierbase.toString()
        agemini.text = vehicule?.agemini.toString()
        categorie.text = vehicule?.categorieco2
        promo.text = vehicule?.promotion.toString()

        val vehicules: List<Vehicule> = AppDatabaseHelper.getDatabase(requireContext()).favorisDAO()
            .findById(vehicule?.id)
        if (vehicules.isNullOrEmpty()) {
            btn.text = "Ajouter aux favoris"
        } else {
            btn.text = "Retirer des favoris"
        }
        val img: ImageView = view.findViewById(R.id.imageView)
        Picasso.get()
            .load("http://s519716619.onlinehome.fr/exchange/madrental/images/".plus(vehicule!!.image))
            .into(img)

        btn.setOnClickListener {
            val favoris: List<Vehicule> = AppDatabaseHelper.getDatabase(requireContext()).favorisDAO()
                .findById(vehicule.id)
            if (favoris.isNullOrEmpty()) {
                AppDatabaseHelper.getDatabase(requireContext()).favorisDAO().insert(vehicule)
                btn.text = "Ajouter aux favoris"
            } else {
                AppDatabaseHelper.getDatabase(requireContext()).favorisDAO().delete(favoris[0])
                btn.text = "Retirer des favoris"
            }
        }
    }

}