package id.co.foodrecipe.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.co.foodrecipe.models.FoodRecipe
import id.co.foodrecipe.util.Constans.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}