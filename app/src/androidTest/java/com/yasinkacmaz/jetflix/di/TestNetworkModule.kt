package com.yasinkacmaz.jetflix.di

import com.yasinkacmaz.jetflix.service.ConfigurationService
import com.yasinkacmaz.jetflix.service.MovieService
import com.yasinkacmaz.jetflix.service.TestMovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideMovieService(): MovieService = TestMovieService()

    @Provides
    @Singleton
    fun provideConfigurationService(): ConfigurationService = TestConfigurationService(listOf())
}
