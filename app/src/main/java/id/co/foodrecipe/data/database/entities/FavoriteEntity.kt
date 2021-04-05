package id.co.foodrecipe.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.co.foodrecipe.models.Result
import id.co.foodrecipe.util.Constans

@Entity(tableName = Constans.FAVORITE_RECIPES_TABLE)
data class FavoriteEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var result: Result
)