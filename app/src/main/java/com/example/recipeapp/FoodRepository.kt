package com.example.recipeapp
import androidx.lifecycle.LiveData

class FoodRepository(private val foodDao:FoodDao) {
    val allFoods: LiveData<List<Food>> = foodDao.getAllFood()

    suspend fun add(food: Food){
        foodDao.add(food)
    }
    suspend fun delete(food: Food){
        foodDao.delete(food)
    }

}