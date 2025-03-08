package com.receipesta.receipefinderapp.converter

import androidx.room.TypeConverter
import com.receipesta.receipefinderapp.network.Recipe
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromRecipe(recipe: Recipe?): String? {
        return Gson().toJson(recipe)
    }

    @TypeConverter
    fun toRecipe(json: String?): Recipe? {
        return Gson().fromJson(json, Recipe::class.java)
    }
}