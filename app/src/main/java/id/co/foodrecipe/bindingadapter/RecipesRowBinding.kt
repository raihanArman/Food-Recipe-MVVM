package id.co.foodrecipe.bindingadapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import id.co.foodrecipe.R
import id.co.foodrecipe.models.Result
import id.co.foodrecipe.ui.fragments.recipes.RecipesFragmentDirections
import java.lang.Exception

class RecipesRowBinding {
    companion object{

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipesRowLayout: ConstraintLayout, result: Result){
            Log.d( "onRecipeClickListener: ", "Called")
            recipesRowLayout.setOnClickListener {
                try{
                    val action =
                        RecipesFragmentDirections.actionRecipesFragmentToDetailActivity(result)
                    recipesRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.d("onRecipeClickListener: ", e.toString())
                }
            }
        }

        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImageFromUrl(imaegView: ImageView, imageUrl: String){
            imaegView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_baseline_image_24)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinute(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean){
            if (vegan){
                when(view){
                    is TextView ->{
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView ->{
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }
    }
}