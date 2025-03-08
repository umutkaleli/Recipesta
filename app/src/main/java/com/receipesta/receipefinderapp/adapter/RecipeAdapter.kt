package com.receipesta.receipefinderapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.receipesta.receipefinderapp.R
import com.receipesta.receipefinderapp.network.Recipe
import com.receipesta.receipefinderapp.user_interface.details.DetailsActivity
import org.jsoup.Jsoup


/*
* This adapter used for home page and search page to shows the neccessary data
* in a recycler view and this is the most usable adapter in this project
 */
class RecipeAdapter (val recipeList : List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.HomePageViewHolder>() {

    inner class HomePageViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){
        //Binds the data
        fun bindData(recipe : Recipe){
            val recipeConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.recipe_constraint_layout)
            val title = itemView.findViewById<TextView>(R.id.title_text_view)
            val image = itemView.findViewById<ImageView>(R.id.recipe_image_view)
            val likes = itemView.findViewById<TextView>(R.id.favorites_text_view)
            val readyInMinutes = itemView.findViewById<TextView>(R.id.time_text_view)
            val description = itemView.findViewById<TextView>(R.id.description_text_view)


            description.text = Jsoup.parse(recipe.summary).text()
            readyInMinutes.text = recipe.readyInMinutes.toString()
            likes.text = recipe.aggregateLikes.toString()
            title.text = recipe.title
            image.load(recipe.image){

            }

            //When a recipe clicked it navigates to DetailsPage and sends the necessary data
            recipeConstraintLayout.setOnClickListener {
                val detailsPage = Intent(itemView.context,DetailsActivity::class.java).apply {
                    putExtra("putTitle", recipe.title)
                    putExtra("putPoster", recipe.image)
                    putExtra("putReadyInMinutes", recipe.readyInMinutes)
                    putExtra("putAggregateLikes", recipe.aggregateLikes)
                    putExtra("putSummary", recipe.summary)
                    putExtra("putIngredients", ArrayList(recipe.extendedIngredients))
                    putExtra("putRecipe", recipe)
                }
                itemView.context.startActivity(detailsPage)
            }

       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        return HomePageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipes_row_layout,parent,false))
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        holder.bindData(recipeList[position])
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
}