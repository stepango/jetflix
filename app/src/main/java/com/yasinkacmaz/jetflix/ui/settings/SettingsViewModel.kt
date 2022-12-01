package com.yasinkacmaz.jetflix.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinkacmaz.jetflix.service.ConfigurationService
import com.yasinkacmaz.jetflix.util.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val configurationService: ConfigurationService,
    private val languageDataStore: ILanguageDataStore
) : StatefulViewModel<SettingsViewModel.UiState>() {

    val selectedLanguage = languageDataStore.language
    val onSettingsChanged = MutableLiveData<Unit>()
    override val uiState = MutableStateFlow(UiState())

    fun fetchLanguages() {
        val canFetchLanguages = uiValue.languages.isEmpty() && uiValue.showLoading.not()
        if (canFetchLanguages) {
            viewModelScope.launch {
                uiValue = uiValue.copy(showLoading = true)
                uiValue = try {
                    val languages = configurationService.fetchLanguages().sortedBy(Language::englishName)
                    uiValue.copy(showLoading = false, languages = languages)
                } catch (exception: Exception) {
                    uiValue.copy(showLoading = false)
                }
            }
        }
    }

    fun onLanguageSelected(language: Language) {
        viewModelScope.launch(Dispatchers.IO) {
            languageDataStore.onLanguageSelected(language)
        }
        onSettingsChanged.value = Unit
    }

    data class UiState(val showLoading: Boolean = false, val languages: List<Language> = emptyList())
}
