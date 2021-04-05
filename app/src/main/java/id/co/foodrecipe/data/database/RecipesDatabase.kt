package id.co.foodrecipe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.co.foodrecipe.data.database.entities.FavoriteEntity
import id.co.foodrecipe.data.database.entities.RecipesEntity


@Database(
    entities = [RecipesEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {
    abstract fun recipesDao() : RecipesDao
}