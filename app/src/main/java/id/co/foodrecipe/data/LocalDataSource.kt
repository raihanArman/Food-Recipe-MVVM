package id.co.foodrecipe.data

import id.co.foodrecipe.data.database.RecipesDao
import id.co.foodrecipe.data.database.entities.FavoriteEntity
import id.co.foodrecipe.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readFavoriteRecipes(): Flow<List<FavoriteEntity>>{
        return recipesDao.readFavoriteRecipes()
    }

    suspend fun insertFavoriteRecipes(favoriteEntity: FavoriteEntity){
        recipesDao.insertFavoriteRecipe(favoriteEntity)
    }

    fun readRecipes(): Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    suspend fun deleteFavoriteRecipes(favoriteEntity: FavoriteEntity){
        recipesDao.deleteFavoriteRecipe(favoriteEntity)
    }

    suspend fun deleteAllFavorite(){
        recipesDao.deleteAllFavoriteRecipes()
    }
}