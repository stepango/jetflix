import com.yasinkacmaz.jetflix.ui.settings.ILanguageDataStore
import com.yasinkacmaz.jetflix.ui.settings.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.junit.Assert

/**
 * Copy of the implementation from unit tests, ideally should be extracted to separate module
 * and added to testImplementation and androidTestImplementation
 */
class TestLanguageDataStore(
    override val language: Flow<Language>,
) : ILanguageDataStore {
    override val languageCode: Flow<String> = language.map { it.iso6391 }
    private var selectedLanguage: Language? = null
    override suspend fun onLanguageSelected(language: Language) {
        selectedLanguage = language;
    }

    fun verifyLanguageSelected(expected: Language) {
        Assert.assertEquals(expected, selectedLanguage)
    }
}