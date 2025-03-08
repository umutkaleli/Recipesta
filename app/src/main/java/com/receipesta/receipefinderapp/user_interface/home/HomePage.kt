
package com.receipesta.receipefinderapp.user_interface.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.receipesta.receipefinderapp.R
import com.receipesta.receipefinderapp.databinding.ActivityHomePageBinding
import com.receipesta.receipefinderapp.network.ApiClient
import com.receipesta.receipefinderapp.network.RecipeResponse
import com.receipesta.receipefinderapp.user_interface.favorites.FavoritesPage
import com.receipesta.receipefinderapp.adapter.RecipeAdapter
import com.receipesta.receipefinderapp.user_interface.search.SearchPage
import com.receipesta.receipefinderapp.user_interface.home.HomePage.MealAndDietType.mealType
import com.receipesta.receipefinderapp.user_interface.home.bottomsheet.HomePageBottomSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomePage : AppCompatActivity() {
    object MealAndDietType{
    val mealType = MutableLiveData<String>()
    }

    private lateinit var binding : ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)


        mealType.postValue("main course")

        mealType.observe(this, Observer{ newName->
            val client = ApiClient.apiService.fetchWithMealType(mealType.value!!
            )

            client.enqueue(object : Callback<RecipeResponse>{

                override fun onResponse(
                    call: Call<RecipeResponse>,
                    response: Response<RecipeResponse>
                ){
                    if(response.isSuccessful){
                        Log.d("recipes",""+response.body())

                        val recipe = response.body()?.recipe

                        recipe?.let {
                            val adapter = RecipeAdapter(recipe)
                            val recyclerView = binding.recyclerView
                            recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
                            recyclerView.adapter = adapter

                        }
                    }
                }
                override fun onFailure(call : Call<RecipeResponse>, t: Throwable){
                    Log.e("failed",""+t.message)
                }
            })
        })

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
        binding.recipesFab.setOnClickListener{
            HomePageBottomSheet().show(supportFragmentManager,"Bottom Sheet")

        }

        setContentView(binding.root)
    }


}

