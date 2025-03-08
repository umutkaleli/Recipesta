package com.receipesta.receipefinderapp.database

import androidx.room.*


/**
 * Database access object to access RecipeDatabase
 */
@Dao
interface RecipeDao {

    //This query selects all favorite recipes
    @Query("SELECT * FROM RecipeDatabase ORDER BY title ASC")
    fun getFavoriteRecipes() : List<RecipeDatabase>

    //This query selects a current recipe according to its title
    @Query("SELECT * FROM RecipeDatabase WHERE title = :title")
    fun getARecipeByTitle(title: String) : RecipeDatabase

    //This function is used for inserting a recipe to the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: RecipeDatabase)

    //This function is used when user clicked a favorited button deletes the current recipe
    //from the database
    @Delete
    fun delete(recipe: RecipeDatabase)
}