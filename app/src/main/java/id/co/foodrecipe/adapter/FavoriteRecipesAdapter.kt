package id.co.foodrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.foodrecipe.data.database.entities.FavoriteEntity
import id.co.foodrecipe.data.database.entities.RecipesEntity
import id.co.foodrecipe.databinding.FavoriteRecipesRowLayoutBinding
import id.co.foodrecipe.ui.fragments.favorite.FavoriteRecipesFragmentDirections
import id.co.foodrecipe.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.favorite_recipes_row_layout.view.*

class FavoriteRecipesAdapter: RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {

    private var favoriteRecipes = emptyList<FavoriteEntity>()

    class MyViewHolder(private val binding: FavoriteRecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteEntity: FavoriteEntity){
            binding.favoriteEntity = favoriteEntity
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(inflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int = favoriteRecipes.size
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedRecipe = favoriteRecipes[position]
        holder.bind(selectedRecipe)

        holder.itemView.favoriteRecipesRowLayout.setOnClickListener{
            val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailActivity(selectedRecipe.result)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(newFavoriteRecipes: List<FavoriteEntity>){
        val favoriteRecipeDiffUtil =
                RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipeDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

}