package com.receipesta.receipefinderapp.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe (
    @Json(name="title")
    val title : String,
    @Json(name="image")
    val image : String,
    @Json(name="readyInMinutes")
    val readyInMinutes : Int,
    @Json(name="aggregateLikes")
    val aggregateLikes : Int,
    @Json(name="summary")
    val summary : String,
    @Json(name="extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>
) : Parcelable

@Parcelize
data class ExtendedIngredient(
    @Json(name="amount")
    val amount: Double,
    @Json(name="consistency")
    val consistency: String,
    @Json(name="image")
    val image: String,
    @Json(name="name")
    val name: String,
    @Json(name="nameClean")
    val nameClean: String,
    @Json(name="original")
    val original: String,
    @Json(name="unit")
    val unit: String
) : Parcelable

data class RecipeResponse(
    @Json(name="results")
    val recipe : List<Recipe>
)

