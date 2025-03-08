package com.receipesta.receipefinderapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
* This object stores base url for api get request and api key since the api is private
 */
object ApiClient {

    private val BASE_URL = "https://api.spoonacular.com/recipes/"
    const val APİ_KEY = "be08099a6ef048ba917aaecbe88080a5"

    //It uses the moshi for converting
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit : Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    //İnitialize apiservice by lazyly
    val apiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}


interface ApiService{
    //get request for a specific meal type
    @GET("complexSearch?number=10&apiKey=${ApiClient.APİ_KEY}&addRecipeInformation=true&fillIngredients=true&")
    fun fetchWithMealType(@Query("type") type : String) :Call<RecipeResponse>

    //get request for a specific name
    @GET("complexSearch?number=2&apiKey=${ApiClient.APİ_KEY}&addRecipeInformation=true&fillIngredients=true&")
    fun searchRecipes(@Query("query") query : String) : Call<RecipeResponse>
}