package com.yasinkacmaz.jetflix.ui.settings

import com.yasinkacmaz.jetflix.service.ConfigurationService
import com.yasinkacmaz.jetflix.test.BaseJetflixTest
import com.yasinkacmaz.jetflix.ui.settings.SettingsViewModel.UiState
import com.yasinkacmaz.jetflix.util.CoroutineTestRule
import com.yasinkacmaz.jetflix.util.OnlyAllowNamedArgs
import com.yasinkacmaz.jetflix.util.test
import com.yasinkacmaz.jetflix.util.viewmodel.StatefulViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class SettingsViewModelTest: BaseJetflixTest() {

    @Test
    fun `fetchLanguages executes successfully and result is sorted`() {
        // Test init
        val languages = listOf(
            Language(englishName = "2", "", ""),
            Language(englishName = "1", "", "")
        )
        val languagesSorted = listOf(
            Language(englishName = "1", "", ""),
            Language(englishName = "2", "", "")
        )
        val settingsViewModel = settingsViewModel(languages)

        assertEvents(
            from = settingsViewModel,
            producedBy = {
                settingsViewModel.fetchLanguages()
            },
            expectedResult = listOf(
                UiState(showLoading = false),
                UiState(showLoading = true),
                UiState(showLoading = false, languagesSorted)
            )
        )
    }

    @Test
    fun `fetchLanguages error`() {
        val settingsViewModel = settingsViewModel(
            listOf(),
            throwConfigurationServiceException = true
        )

        assertEvents(
            from = settingsViewModel,
            producedBy = {
                settingsViewModel.fetchLanguages()
            },
            expectedResult = listOf(
                UiState(showLoading = false),
                UiState(showLoading = true),
                UiState(showLoading = false),
            )
        )
    }
}

fun settingsViewModel(
    languages: List<Language>,
    throwConfigurationServiceException: Boolean = false,
    configurationService: TestConfigurationService = TestConfigurationService(
        languages,
        throwConfigurationServiceException
    ),
    languageDataStore: TestLanguageDataStore = TestLanguageDataStore(languages.asFlow()),
): SettingsViewModel {
    return SettingsViewModel(configurationService, languageDataStore)
}

class TestConfigurationService(
    private val languages: List<Language>,
    private val throwException: Boolean = false
) :
    ConfigurationService {
    override suspend fun fetchLanguages() = if (!throwException) languages else throw IOException()
}

class TestLanguageDataStore(
    override val language: Flow<Language>,
) : ILanguageDataStore {
    override val languageCode: Flow<String> = language.map { it.iso6391 }
    private var selectedLanguage: Language? = null
    override suspend fun onLanguageSelected(language: Language) {
        selectedLanguage = language;
    }

    fun verifyLanguageSelected(expected: Language) {
        assertEquals(expected, selectedLanguage)
    }
}

/**
 * StatefulViewModel assertEvents
 */
fun <E : Any> assertEvents(
    vararg onlyAllowNamedArgs: OnlyAllowNamedArgs,
    from : StatefulViewModel<E>,
    producedBy: () -> Unit,
    expectedResult: List<E>
) {
    assertEvents(
        onlyAllowNamedArgs = onlyAllowNamedArgs,
        from = from.uiState,
        producedBy = producedBy,
        expectedResult = expectedResult,
    )
}

/**
 * Generic assertEvents
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <E : Any> assertEvents(
    vararg onlyAllowNamedArgs: OnlyAllowNamedArgs,
    from: Flow<E>,
    producedBy: () -> Unit,
    expectedResult: List<E>
) {
    assert(onlyAllowNamedArgs.isEmpty())
    lateinit var states: List<E>
    runTest {
        states = from.test()
        // Perform Actions
        producedBy()
    }
    assertEquals(expectedResult, states)
}

