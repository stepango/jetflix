package com.yasinkacmaz.jetflix.ui.filter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface IFilterDataStore {
    val filterState: Flow<FilterState>

    suspend fun onFilterStateChanged(filterState: FilterState)

    suspend fun resetFilterState()

}

class FilterDataStore(private val json: Json, private val preferences: DataStore<Preferences>) : IFilterDataStore {
    override val filterState: Flow<FilterState> = preferences.data
        .map { preferences ->
            val filterStateString = preferences[KEY_FILTER_STATE]
            if (filterStateString != null) {
                json.decodeFromString(filterStateString)
            } else {
                FilterState()
            }
        }
        .catch {
            emit(FilterState())
        }

    override suspend fun onFilterStateChanged(filterState: FilterState) {
        preferences.edit { preferences ->
            preferences[KEY_FILTER_STATE] = json.encodeToString(filterState)
        }
    }

    override suspend fun resetFilterState() {
        preferences.edit { preferences ->
            preferences[KEY_FILTER_STATE] = json.encodeToString(FilterState())
        }
    }

    companion object {
        val KEY_FILTER_STATE = stringPreferencesKey("filter_state")
    }
}
