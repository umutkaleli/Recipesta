package com.receipesta.receipefinderapp.user_interface.details



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.receipesta.receipefinderapp.R
import com.receipesta.receipefinderapp.adapter.ViewPagerAdapter
import com.receipesta.receipefinderapp.application.RecipeApplication
import com.receipesta.receipefinderapp.database.RecipeDatabase
import com.receipesta.receipefinderapp.network.ExtendedIngredient
import com.receipesta.receipefinderapp.network.Recipe
import com.receipesta.receipefinderapp.user_interface.details.fragments.CommentsFragment
import com.receipesta.receipefinderapp.user_interface.details.fragments.IngredientsFragment
import com.receipesta.receipefinderapp.user_interface.details.fragments.OverviewFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class DetailsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var favoritesIcon: ImageView

    private var recipeSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        toolbar = findViewById(R.id.toolbar)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        favoritesIcon = findViewById(R.id.favorites_icon)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true) // Hide default title

        setupViewPager()
        tabLayout.setupWithViewPager(viewPager)


        val title = intent.getStringExtra("putTitle")
        val poster = intent.getStringExtra("putPoster")
        val readyInMinutes = intent.getIntExtra("putReadyInMinutes", 0)
        val aggregateLikes = intent.getIntExtra("putAggregateLikes", 0)
        val summary = intent.getStringExtra("putSummary")
        val ingredients = intent.getParcelableArrayListExtra<ExtendedIngredient>("putIngredients")
        val recipe = intent.getParcelableExtra<Recipe>("putRecipe")
        val summaryParsed = summary?.let { Jsoup.parse(it).text() }


        // Pass the data to the fragments as needed
        val overviewFragment = OverviewFragment().apply {
            arguments = Bundle().apply {
                putString("titleKey", title)
                putString("posterKey", poster)
                putInt("readyInMinutesKey", readyInMinutes)
                putInt("aggregateLikesKey", aggregateLikes)
                putString("summaryKey", summaryParsed)
            }
        }

        val ingredientsFragment = IngredientsFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("ingredientsKey", ingredients)
            }
        }

        // Create and set up the ViewPagerAdapter with the fragments
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(overviewFragment, "Overview")
        adapter.addFragment(ingredientsFragment, "Ingredients")
        // Add more fragments and data as needed
        viewPager.adapter = adapter


        val application = application as RecipeApplication
        val database = application.database
        val recipeDao = database.RecipeDao()

        lifecycleScope.launch {
            val savedRecipes = withContext(Dispatchers.IO) {
                recipeDao.getFavoriteRecipes()
            }

            for (savedRecipe in savedRecipes) {
                if (savedRecipe.title == title) {
                    recipeSaved = true
                    favoritesIcon.setImageResource(R.drawable.ic_favorite)
                    break
                }
            }

            if (!recipeSaved) {
                favoritesIcon.setImageResource(R.drawable.ic_favorite_border)
            }

            favoritesIcon.setOnClickListener {
                lifecycleScope.launch {
                    if (recipeSaved) {
                        // Recipe is already saved as a favorite, so delete it
                        withContext(Dispatchers.IO) {
                            val recipefav =if (title != null) {
                                if (recipe != null) {
                                    RecipeDatabase(title,recipe)
                                } else {
                                    null
                                }
                            } else {
                                null
                            }
                            if (recipefav != null) {
                                recipeDao.delete(recipefav)
                            }
                        }
                        favoritesIcon.setImageResource(R.drawable.ic_favorite_border)
                        Toast.makeText(
                            this@DetailsActivity,
                            "Recipe removed from favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                        recipeSaved = false
                    } else {
                        // Recipe is not saved as a favorite, so insert it
                        val recipeFavorite = RecipeDatabase(
                            title!!,
                            recipe!!
                        )
                        withContext(Dispatchers.IO) {
                            recipeDao.insert(recipeFavorite)
                        }
                        favoritesIcon.setImageResource(R.drawable.ic_favorite)
                        Toast.makeText(
                            this@DetailsActivity,
                            "Recipe added to favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                        recipeSaved = true
                    }
                }
            }
        }
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // Add your fragments to the adapter
        adapter.addFragment(OverviewFragment(), "Overview")
        adapter.addFragment(IngredientsFragment(), "Ingredients")
        adapter.addFragment(CommentsFragment(), "Comments")

        viewPager.adapter = adapter
    }
}
