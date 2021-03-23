package id.co.foodrecipe.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import id.co.foodrecipe.util.Constans
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private object PreferencesKeys{
        val selectedMealType = preferencesKey<String>(Constans.PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = preferencesKey<Int>(Constans.PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(Constans.PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(Constans.PREFERENCES_DIET_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
            name = Constans.PREFERENCES_NAME
    )

    suspend fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.selectedMealType] = mealType
            preferences[PreferencesKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferencesKeys.selectedDietType] = dietType
            preferences[PreferencesKeys.selectedDietTypeId] = dietTypeId
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
            .catch { exception ->
                if (exception is IOException){
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }
            .map { preferences ->
                val selectedMealType = preferences[PreferencesKeys.selectedMealType] ?: Constans.DEFAULT_MEAL_TYPE
                val selectedMealTypeId = preferences[PreferencesKeys.selectedMealTypeId] ?: 0
                val selectedDietType = preferences[PreferencesKeys.selectedMealType] ?: Constans.DEFAULT_DIET_TYPE
                val selectedDietTypeId = preferences[PreferencesKeys.selectedMealTypeId] ?: 0
                MealAndDietType(
                        selectedMealType,
                        selectedMealTypeId,
                        selectedDietType,
                        selectedDietTypeId
                )
            }

}

data class MealAndDietType(
        val selectedMealType: String,
        val selectedMealTypeId: Int,
        val selectedDietType: String,
        val selectedDietTypeId: Int
)