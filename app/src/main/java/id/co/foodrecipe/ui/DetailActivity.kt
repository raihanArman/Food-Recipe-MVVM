package id.co.foodrecipe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.co.foodrecipe.R
import id.co.foodrecipe.adapter.PagerAdapter
import id.co.foodrecipe.data.database.entities.FavoriteEntity
import id.co.foodrecipe.ui.fragments.ingridients.IngridientsFragment
import id.co.foodrecipe.ui.fragments.instructions.InstructionsFragment
import id.co.foodrecipe.ui.fragments.overview.OverviewFragment
import id.co.foodrecipe.util.Constans.Companion.RECIPE_RESULT_KEY
import id.co.foodrecipe.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val args by navArgs<DetailActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var recipeSaved = false
    private var saveRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngridientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val adapter = PagerAdapter(
                resultBundle,
                fragments,
                titles,
                supportFragmentManager
        )

        viewPager.adapter = adapter
        tab_layout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorite_menu)
        checkSavedRecipes(menuItem!!)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this, Observer { favoriteEntity ->
            try{
                for(savedRecipe in favoriteEntity){
                    if(savedRecipe.result.recipeId == args.result.recipeId){
                        changeMenuItemColor(menuItem, R.color.yellow)
                        saveRecipeId = savedRecipe.id
                        recipeSaved = true
                    }else{
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            }catch (e: Exception){
                Log.d("DetailActivity", "checkSavedRecipes: ${e.message}")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }else if(item.itemId == R.id.save_to_favorite_menu && !recipeSaved){
            saveToFavorite(item)
        }else if(item.itemId == R.id.save_to_favorite_menu && recipeSaved){
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorite(item: MenuItem) {
        val favoriteEntity =
                FavoriteEntity(
                        0,
                        args.result
                )
        mainViewModel.insertFavoriteRecipe(favoriteEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved")
        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem){
        val favoriteEntity =
                FavoriteEntity(
                        saveRecipeId,
                        args.result
                )
        mainViewModel.deleteFavoriteRecipe(favoriteEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from favorites")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
                detail_layout,
                message,
                Snackbar.LENGTH_LONG
        ).setAction("Okay"){}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}