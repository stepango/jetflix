package com.yasinkacmaz.jetflix.ui.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface ILanguageDataStore {
    val language: Flow<Language>
    val languageCode: Flow<String>
    suspend fun onLanguageSelected(language: Language)
}

class LanguageDataStore(private val json: Json, private val preferences: DataStore<Preferences>) : ILanguageDataStore {

    override val language: Flow<Language> = preferences.data
        .map { preferences ->
            val languageString = preferences[KEY_LANGUAGE]
            if (languageString != null) {
                json.decodeFromString(languageString)
            } else {
                Language.default
            }
        }

    override val languageCode: Flow<String> = language.map { it.iso6391 }

    override suspend fun onLanguageSelected(language: Language) {
        preferences.edit { preferences ->
            preferences[KEY_LANGUAGE] = json.encodeToString(language)
        }
    }

    companion object {
        val KEY_LANGUAGE = stringPreferencesKey("language")
    }
}
