/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yasinkacmaz.jetflix.espresso

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yasinkacmaz.jetflix.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class JetnewsTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun search_bar_shows_on_start() {
        composeTestRule.onNodeWithText("Search Movies").assertExists()
    }

    @Test
    fun app_opensArticle() {
        composeTestRule.onNodeWithText(text = "Scarface", substring = true).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(text = "Scarface").assertExists()
        composeTestRule.onNodeWithText(text = "7633").assertExists()
        composeTestRule.onNodeWithText(text = "Drama").assertExists()
    }

}


