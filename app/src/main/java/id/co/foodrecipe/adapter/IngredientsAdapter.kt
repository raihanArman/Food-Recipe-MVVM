package id.co.foodrecipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.co.foodrecipe.R
import id.co.foodrecipe.models.ExtendedIngredient
import id.co.foodrecipe.util.Constans.Companion.BASE_IMAGE_URL
import id.co.foodrecipe.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.ingredients_imageView.load(BASE_IMAGE_URL+ingredientsList[position].image){
            crossfade(600)
            error(R.drawable.ic_baseline_image_24)
        }
        holder.itemView.ingredients_name.text = ingredientsList[position].name.capitalize()
        holder.itemView.ingredients_count.text = ingredientsList[position].amount.toString()
        holder.itemView.ingredients_unit.text = ingredientsList[position].unit
        holder.itemView.ingredients_consistency.text = ingredientsList[position].consistency
        holder.itemView.ingredients_original.text = ingredientsList[position].original
    }

    fun setData(newIngredient: List<ExtendedIngredient>){
        val recipesDiffUtil =
                RecipesDiffUtil(ingredientsList, newIngredient)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        ingredientsList = newIngredient
        diffUtilResult.dispatchUpdatesTo(this)
    }

}