package com.yasinkacmaz.jetflix.di

import com.yasinkacmaz.jetflix.service.ConfigurationService
import com.yasinkacmaz.jetflix.ui.settings.Language
import java.io.IOException

class TestConfigurationService(
    private val languages: List<Language>,
    private val throwException: Boolean = false
) : ConfigurationService {
    override suspend fun fetchLanguages() = if (!throwException) languages else throw IOException()
}