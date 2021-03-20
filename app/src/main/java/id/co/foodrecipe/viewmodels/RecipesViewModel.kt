package id.co.foodrecipe.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.co.foodrecipe.util.Constans
import id.co.foodrecipe.util.Constans.Companion.API_KEY
import id.co.foodrecipe.util.Constans.Companion.QUERY_ADD_RECIPE_INFORMATION
import id.co.foodrecipe.util.Constans.Companion.QUERY_APIKEY
import id.co.foodrecipe.util.Constans.Companion.QUERY_DIET
import id.co.foodrecipe.util.Constans.Companion.QUERY_FILL_INGEDIENTS
import id.co.foodrecipe.util.Constans.Companion.QUERY_NUMBER
import id.co.foodrecipe.util.Constans.Companion.QUERY_TYPE

class RecipesViewModel(application: Application): AndroidViewModel(application) {
    fun applyQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_APIKEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGEDIENTS] = "true"
        return queries
    }
}