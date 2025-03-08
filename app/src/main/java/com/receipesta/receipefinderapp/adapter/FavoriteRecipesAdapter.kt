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
import com.receipesta.receipefinderapp.database.RecipeDatabase
import com.receipesta.receipefinderapp.user_interface.details.DetailsActivity


/*
*This adapter takes a recipeList information from the local database
* and shows the items in a recycler view
 */
class FavoriteRecipesAdapter (val recipeList : List<RecipeDatabase>) : RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteRecipesViewHolder>() {

    inner class FavoriteRecipesViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindData(recipe : RecipeDatabase){
            //Takes the items from the layout file
            val recipeConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.favorites_recipe_constraint_layout)
            val title = itemView.findViewById<TextView>(R.id.favorites_title_text_view)
            val image = itemView.findViewById<ImageView>(R.id.favorites_recipe_image_view)
            val likes = itemView.findViewById<TextView>(R.id.favorites_text_view)
            val readyInMinutes = itemView.findViewById<TextView>(R.id.favorites_time_text_view)
            val description = itemView.findViewById<TextView>(R.id.favorites_description_text_view)


            description.text = recipe.recipe.summary
            readyInMinutes.text = recipe.recipe.readyInMinutes.toString()
            likes.text = recipe.recipe.aggregateLikes.toString()
            title.text = recipe.recipe.title
            image.load(recipe.recipe.image){

            }

            //When a recipe clicked it navigates to DetailsPage and sends the necessary data
            recipeConstraintLayout.setOnClickListener {
                val detailsPage = Intent(itemView.context,DetailsActivity::class.java).apply {
                    putExtra("putTitle", recipe.recipe.title)
                    putExtra("putPoster", recipe.recipe.image)
                    putExtra("putReadyInMinutes", recipe.recipe.readyInMinutes)
                    putExtra("putAggregateLikes", recipe.recipe.aggregateLikes)
                    putExtra("putSummary", recipe.recipe.summary)
                    putExtra("putIngredients", ArrayList(recipe.recipe.extendedIngredients))
                    putExtra("putRecipe",recipe.recipe)
                }
                itemView.context.startActivity(detailsPage)
            }

        }
    }

    //These 3 methods for the boiler plate code for an adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipesViewHolder {
        return FavoriteRecipesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favorite_recipes_row_layout,parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteRecipesViewHolder, position: Int) {
        holder.bindData(recipeList[position])
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
}