package com.yasinkacmaz.jetflix.test

import com.yasinkacmaz.jetflix.util.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

open class BaseJetflixTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
}