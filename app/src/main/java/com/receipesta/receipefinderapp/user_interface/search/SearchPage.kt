package com.receipesta.receipefinderapp.user_interface.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.receipesta.receipefinderapp.R
import com.receipesta.receipefinderapp.databinding.ActivitySearchPageBinding
import com.receipesta.receipefinderapp.network.ApiClient
import com.receipesta.receipefinderapp.network.RecipeResponse
import com.receipesta.receipefinderapp.user_interface.favorites.FavoritesPage
import com.receipesta.receipefinderapp.user_interface.home.HomePage
import com.receipesta.receipefinderapp.adapter.RecipeAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchPage : AppCompatActivity() {
    private lateinit var binding: ActivitySearchPageBinding
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        searchEditText = binding.searchEditText
        val searchButton = binding.searchButton

        val backButton = binding.backButton

        val mBottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        mBottomNavigationView.menu.findItem(R.id.search_icon).isChecked = true

        searchButton.setOnClickListener {
            val client = ApiClient.apiService.searchRecipes(
                searchEditText.text.toString()
            )

            client.enqueue(object : Callback<RecipeResponse> {

                override fun onResponse(
                    call: Call<RecipeResponse>,
                    response: Response<RecipeResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("recipes", "" + response.body())

                        val recipe = response.body()?.recipe

                        recipe?.let {
                            val adapter = RecipeAdapter(recipe)
                            val recyclerView = binding.recyclerView
                            recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
                            recyclerView.adapter = adapter

                        }
                    }
                }

                override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                    Log.e("failed", "" + t.message)
                }
            })
        }

        backButton.setOnClickListener{
            val homeIntent = Intent(this, HomePage::class.java)
            startActivity(homeIntent)
        }


        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
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