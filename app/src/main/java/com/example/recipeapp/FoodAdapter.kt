package com.example.recipeapp

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


@Suppress("DEPRECATION")
class FoodAdapter(val context: Context, val listener: RowClickListener):
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var foods = emptyList<Food>()
    inner class FoodViewHolder(itemView: View, val listener: RowClickListener) : RecyclerView.ViewHolder(
            itemView
    ){
        val foodNameTextView : TextView = itemView.findViewById(R.id.textFoodName)
        val foodIngrTextView : TextView = itemView.findViewById(R.id.textFoodIngr)
        val foodStepsTextView : TextView = itemView.findViewById(R.id.textFoodSteps)
        val foodCatTextView : TextView = itemView.findViewById(R.id.textFoodCat)

        val imageDel : ImageView = itemView.findViewById(R.id.imageDelete)
        var imgActivity : ImageView = itemView.findViewById(R.id.activityImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = inflater.inflate(R.layout.content_main, parent, false)
        return FoodViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val current = foods[position]


        //use glide to load and store the image
        Glide.with(context)
            .load(current.image)
            .into(holder.imgActivity)
        //bind the data from database into the view holder
        holder.foodNameTextView.text = current.foName
        holder.foodIngrTextView.text = current.foIngr
        holder.foodStepsTextView.text = current.foSteps
        holder.foodCatTextView.text = current.foCtgry

        //the text for ingredients and steps is scrollable but not smoooth
        holder.foodIngrTextView.movementMethod = ScrollingMovementMethod.getInstance()
        holder.foodStepsTextView.movementMethod = ScrollingMovementMethod.getInstance()

        holder.imageDel.setOnClickListener{
            listener.onDeleteFoodClickListener(current)
        }

    }

    interface RowClickListener{
        fun onDeleteFoodClickListener(food: Food)
    }
    internal fun setFoods(foods: List<Food>){
        this.foods = foods

        //the data will keep updated
        notifyDataSetChanged()
    }
    override fun getItemCount() = foods.size
}

