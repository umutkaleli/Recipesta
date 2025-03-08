package com.receipesta.receipefinderapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.receipesta.receipefinderapp.R
import com.receipesta.receipefinderapp.network.ExtendedIngredient
import java.util.*


/*
*This adapter takes ingredients for a current recipe and shows them in a recycler view
 */
class IngredientsAdapter(private val ingredientsList : List<ExtendedIngredient>) : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>(){

    //This is the base image url for the api service
    private val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout, parent, false))
    }

    /*
    * This function loads the necessary data to the ingrediens row layout for a recycler view
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.ingredient_imageView).load(BASE_IMAGE_URL + ingredientsList[position].image)
        holder.itemView.findViewById<TextView>(R.id.ingredient_name).text = ingredientsList[position].name.capitalize(Locale.ROOT)
        holder.itemView.findViewById<TextView>(R.id.ingredient_amount).text = ingredientsList[position].amount.toString()
        holder.itemView.findViewById<TextView>(R.id.ingredient_unit).text = ingredientsList[position].unit
        holder.itemView.findViewById<TextView>(R.id.ingredient_consistency).text = ingredientsList[position].consistency
        holder.itemView.findViewById<TextView>(R.id.ingredient_original).text = ingredientsList[position].original
    }

    // This method returns the number of ingredients for a current recipe
    override fun getItemCount(): Int {
        return ingredientsList.size
    }
}