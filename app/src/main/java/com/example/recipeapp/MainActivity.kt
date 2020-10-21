package com.example.recipeapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), FoodAdapter.RowClickListener {
    private lateinit var foodViewModel : FoodViewModel
    private val REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = FoodAdapter(this, this@MainActivity)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        foodViewModel.allFoods.observe(this, Observer{
                foods -> foods?.let{
            adapter.setFoods(it)}
            
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddRecipe::class.java)
            startActivityForResult(intent, REQUEST_CODE)

        }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val confirmMsg = "Food recipe is added"
        val notConfirmMsg = "Food recipe is not added"
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.let{
                val food = Food(it.getStringExtra("foodName"), it.getStringExtra("foodImg"),
                    it.getStringExtra("foodIngr"), it.getStringExtra("foodSteps"),
                    it.getStringExtra("foodCtgry"))
                foodViewModel.addFood(food)

                Toast.makeText(applicationContext, confirmMsg, Toast.LENGTH_SHORT).show()
            }
        }else{

            Toast.makeText(applicationContext, notConfirmMsg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDeleteFoodClickListener(food: Food) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Delete Alert")
        builder.setMessage("Are you sure to delete?")

        builder.setNegativeButton(R.string.cancel) { dialog, which ->
            Toast.makeText(applicationContext, R.string.cancelMsg, Toast.LENGTH_SHORT).show()
        }
        builder.setPositiveButton(R.string.confirm) { dialog, which ->
            Toast.makeText(applicationContext, R.string.confirmMsg, Toast.LENGTH_SHORT).show()
            foodViewModel.deleteFood(food)
        }
        builder.show()

    }
}

