package com.example.recipeapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : FoodRepository

    val allFoods: LiveData<List<Food>>


    init{
        //Gets reference to UserDao from UserRoomDatabase to construct the correct UserRepository
        val foodDao = FoodDatabase.get(application).foodDao()
        repository = FoodRepository((foodDao))
        allFoods = repository.allFoods
    }

    //ViewModel uses a coroutine to perform long running operations
    fun addFood(food: Food) = viewModelScope.launch{
        repository.add(food)

    }
    fun deleteFood(food: Food) = viewModelScope.launch{
        repository.delete(food)

    }

}