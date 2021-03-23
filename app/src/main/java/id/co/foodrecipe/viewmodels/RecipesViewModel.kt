package id.co.foodrecipe.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import id.co.foodrecipe.data.DataStoreRepository
import id.co.foodrecipe.data.MealAndDietType
import id.co.foodrecipe.util.Constans
import id.co.foodrecipe.util.Constans.Companion.API_KEY
import id.co.foodrecipe.util.Constans.Companion.QUERY_ADD_RECIPE_INFORMATION
import id.co.foodrecipe.util.Constans.Companion.QUERY_APIKEY
import id.co.foodrecipe.util.Constans.Companion.QUERY_DIET
import id.co.foodrecipe.util.Constans.Companion.QUERY_FILL_INGEDIENTS
import id.co.foodrecipe.util.Constans.Companion.QUERY_NUMBER
import id.co.foodrecipe.util.Constans.Companion.QUERY_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipesViewModel @ViewModelInject constructor(application: Application, private val dataStoreRepository: DataStoreRepository): AndroidViewModel(application) {

    private var mealType = Constans.DEFAULT_MEAL_TYPE
    private var dietType = Constans.DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
            viewModelScope.launch(Dispatchers.IO) {
                dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
            }

    fun applyQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect{value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = Constans.DEFAULT_RECIPES_NUMBER
        queries[QUERY_APIKEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGEDIENTS] = "true"
        return queries
    }
}