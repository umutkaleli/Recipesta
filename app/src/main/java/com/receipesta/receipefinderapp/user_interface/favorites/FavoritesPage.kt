package com.receipesta.receipefinderapp.user_interface.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.receipesta.receipefinderapp.R
import com.receipesta.receipefinderapp.adapter.FavoriteRecipesAdapter
import com.receipesta.receipefinderapp.application.RecipeApplication
import com.receipesta.receipefinderapp.database.RecipeDatabase
import com.receipesta.receipefinderapp.user_interface.home.HomePage
import com.receipesta.receipefinderapp.user_interface.search.SearchPage
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesPage : AppCompatActivity() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var favoriteRecipesAdapter: FavoriteRecipesAdapter
    private lateinit var favoriteRecipes: List<RecipeDatabase>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_page)

        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Favorite Recipes"

        favoritesRecyclerView = findViewById(R.id.favoriteRecipes_recyclerView)
        favoritesRecyclerView.layoutManager = LinearLayoutManager(this)

        val application = application as RecipeApplication
        val database = application.database
        val recipeDao = database.RecipeDao()

        // Replace `favoriteRecipes` with your actual list of favorite recipes
        lifecycleScope.launch {
            favoriteRecipes = withContext(Dispatchers.IO) {
                recipeDao.getFavoriteRecipes()
            }
            favoriteRecipesAdapter = FavoriteRecipesAdapter(favoriteRecipes)
            favoritesRecyclerView.adapter = favoriteRecipesAdapter
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.menu.findItem(R.id.favorites_icon).isChecked = true

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_icon -> {
                    val homeIntent = Intent(this, HomePage::class.java)
                    startActivity(homeIntent)
                }
                R.id.search_icon -> {
                    val searchIntent = Intent(this, SearchPage::class.java)
                    startActivity(searchIntent)
                }
                R.id.favorites_icon -> {
                    val favoritesIntent = Intent(this, FavoritesPage::class.java)
                    startActivity(favoritesIntent)
                }
            }
            true
        }
    }
}