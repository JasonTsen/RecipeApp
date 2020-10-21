package com.example.recipeapp

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FoodDao {
    @Query("SELECT * FROM food ORDER BY foCtgry ASC" )
    fun getAllFood(): LiveData<List<Food>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(foods: Food)



    @Delete
    suspend fun delete(food: Food)



}