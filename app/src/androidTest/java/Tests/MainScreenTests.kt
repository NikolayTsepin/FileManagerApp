package com.example.filemanager.tests.main

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.filemanager.MainActivity
import com.example.filemanager.screens.MainScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class WelcomeTextTest : TestCase() {
    @get:Rule

    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun welcomeTextIsDisplayed() {
        run {
            step("Проверка отображения текста") {
                MainScreen {
                    welcomeText {
                        isDisplayed()
                    }
                    goToFilesButton {
                        isDisplayed()
                        click()
                    }
                }
            }
        }
    }
}




