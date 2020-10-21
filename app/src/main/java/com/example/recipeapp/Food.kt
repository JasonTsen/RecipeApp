package com.example.recipeapp

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
@Keep
@Entity(tableName = "food")
data class Food(

    val foName: String,
    var image: String,
    val foIngr: String,
    val foSteps: String,
    val foCtgry: String

){
    @PrimaryKey(autoGenerate = true)
    var foodId: Int = 0


}