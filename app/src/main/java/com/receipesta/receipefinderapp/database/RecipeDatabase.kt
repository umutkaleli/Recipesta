package com.receipesta.receipefinderapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.receipesta.receipefinderapp.network.Recipe


/*
* This is the only entity to store the favorites recipes in the database
 */
@Entity
data class RecipeDatabase(
    //Title is the primary key since this is the unique for every recipe
    @PrimaryKey @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "recipe")
    var recipe : Recipe
    )