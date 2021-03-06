package com.example.recipeapp

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = arrayOf(Food::class), version = 1, exportSchema = false)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object{
        @Volatile
        private var INSTANCE: FoodDatabase? = null
        fun get(context: Context) : FoodDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    FoodDatabase::class.java, "food_database").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }

        }
    }
}