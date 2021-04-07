package id.co.foodrecipe.adapter

import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import id.co.foodrecipe.R
import id.co.foodrecipe.data.database.entities.FavoriteEntity
import id.co.foodrecipe.data.database.entities.RecipesEntity
import id.co.foodrecipe.databinding.FavoriteRecipesRowLayoutBinding
import id.co.foodrecipe.ui.fragments.favorite.FavoriteRecipesFragmentDirections
import id.co.foodrecipe.util.RecipesDiffUtil
import id.co.foodrecipe.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.favorite_recipes_row_layout.view.*

class FavoriteRecipesAdapter(private val requireActivity: FragmentActivity, private val mainViewModel: MainViewModel): RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoriteEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var favoriteRecipes = emptyList<FavoriteEntity>()
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

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
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)

        holder.itemView.favoriteRecipesRowLayout.setOnClickListener{
            if (multiSelection){
                applySelection(holder, currentRecipe)
            }else{
                val action =
                        FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailActivity(currentRecipe.result)
                holder.itemView.findNavController().navigate(action)
            }
        }


        holder.itemView.favoriteRecipesRowLayout.setOnLongClickListener {
            if(!multiSelection){
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            }else{
                multiSelection = false
                false
            }
        }

    }


    fun setData(newFavoriteRecipes: List<FavoriteEntity>){
        val favoriteRecipeDiffUtil =
                RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipeDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int){
        holder.itemView.favoriteRecipesRowLayout.setBackgroundColor(
                ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.itemView.favorite_row_cardView.strokeColor =
                ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle(){
        when(selectedRecipes.size){
            0 ->{
                mActionMode.finish()
            }
            1 ->{
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else ->{
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoriteEntity){
        if (selectedRecipes.contains(currentRecipe)){
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        }else{
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun applyStatusBarColor(color: Int){
        requireActivity.window.statusBarColor =
                ContextCompat.getColor(requireActivity, color)
    }

    private fun showSnackBar(message: String){
        Snackbar.make(
                rootView,
                message,
                Snackbar.LENGTH_LONG
        ).setAction("Okay"){}.show()
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_favorite_recipe_menu){
            selectedRecipes.forEach{
                mainViewModel.deleteFavoriteRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe's removed")
            multiSelection = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach{holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    fun clearContextualActionMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }

}