package id.co.foodrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.foodrecipe.databinding.RecipesRowLayoutBinding
import id.co.foodrecipe.models.FoodRecipe
import id.co.foodrecipe.models.Result
import id.co.foodrecipe.ui.fragments.recipes.RecipesFragment
import id.co.foodrecipe.util.RecipesDiffUtil

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipe = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result){
            binding.result = result
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int = recipe.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipe[position]
        holder.bind(currentRecipe)
    }

    fun setData(newData: FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipe, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipe = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}