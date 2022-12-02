package com.yasinkacmaz.jetflix.di

import com.yasinkacmaz.jetflix.ui.settings.SettingsViewModel
import dagger.Component

@Component(modules = [NetworkModule::class])
interface SettingsTestComponent {

    fun settingsViewModel(): SettingsViewModel

}