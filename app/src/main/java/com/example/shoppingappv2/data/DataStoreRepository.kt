package com.example.shoppingappv2.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.example.shoppingappv2.utli.Constants.Companion.PREFERENCES_PRICE_RANGE_TYPE
import com.example.shoppingappv2.utli.Constants.Companion.PREFERENCES_PRICE_RANGE_TYPE_ID
import com.example.shoppingappv2.utli.Constants.Companion.PREFERENCES_SIZE_TYPE
import com.example.shoppingappv2.utli.Constants.Companion.PREFERENCES_SIZE_TYPE_ID
import com.example.shoppingappv2.utli.Constants.Companion.PREFERENCES_NAME
import com.example.shoppingappv2.utli.Constants.Companion.DEFAULT_SIZE_TYPE
import com.example.shoppingappv2.utli.Constants.Companion.DEFAULT_PRICE_RANGE_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedSizeType = preferencesKey<String>(PREFERENCES_SIZE_TYPE)
        val selectedSizeTypeId = preferencesKey<Int>(PREFERENCES_SIZE_TYPE_ID)
        val selectedPriceRangeType = preferencesKey<String>(PREFERENCES_PRICE_RANGE_TYPE)
        val selectedPriceRangeTypeId = preferencesKey<Int>(PREFERENCES_PRICE_RANGE_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    suspend fun saveSizeAndPriceRange(
        sizeType: String,
        sizeTypeId: Int,
        priceRangeType: String,
        priceRangeTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedSizeType] = sizeType
            preferences[PreferenceKeys.selectedSizeTypeId] = sizeTypeId
            preferences[PreferenceKeys.selectedPriceRangeType] = priceRangeType
            preferences[PreferenceKeys.selectedPriceRangeTypeId] = priceRangeTypeId
        }
    }


    val readSizeAndPriceRange: Flow<SizeAndPriceRangeType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedSizeType = preferences[PreferenceKeys.selectedSizeType] ?: DEFAULT_SIZE_TYPE
            val selectedSizeTypeId = preferences[PreferenceKeys.selectedSizeTypeId] ?: 0
            val selectedPriceRangeType = preferences[PreferenceKeys.selectedPriceRangeType] ?: DEFAULT_PRICE_RANGE_TYPE
            val selectedPriceRangeTypeId = preferences[PreferenceKeys.selectedPriceRangeTypeId] ?: 0
            SizeAndPriceRangeType(
                selectedSizeType,
                selectedSizeTypeId,
                selectedPriceRangeType,
                selectedPriceRangeTypeId
            )
        }
}

data class SizeAndPriceRangeType(
    val sizeType: String,
    val sizeTypeId: Int,
    val priceRangeType: String,
    val priceRangeTypeId: Int
)