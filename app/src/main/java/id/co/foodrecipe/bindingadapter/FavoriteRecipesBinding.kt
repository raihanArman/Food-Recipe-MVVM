package id.co.foodrecipe.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import id.co.foodrecipe.adapter.FavoriteRecipesAdapter
import id.co.foodrecipe.data.database.entities.FavoriteEntity

class FavoriteRecipesBinding {

    companion object{

        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(view: View, favoriteEntity: List<FavoriteEntity>?, mAdapter: FavoriteRecipesAdapter?){
            if (favoriteEntity.isNullOrEmpty()){
                when(view){
                    is ImageView ->{
                        view.visibility = View.VISIBLE
                    }
                    is TextView ->{
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView ->{
                        view.visibility = View.INVISIBLE
                    }
                }
            }else{
                when(view){
                    is ImageView ->{
                        view.visibility = View.INVISIBLE
                    }
                    is TextView ->{
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView ->{
                        view.visibility = View.VISIBLE
                        mAdapter?.setData(favoriteEntity)
                    }
                }
            }
        }
    }

}