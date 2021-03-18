package id.co.foodrecipe.data

import id.co.foodrecipe.data.network.FoodRecipeApi
import id.co.foodrecipe.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipeApi: FoodRecipeApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe>{
        return foodRecipeApi.getRecipes(queries)
    }

}