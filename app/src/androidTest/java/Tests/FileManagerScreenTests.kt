package com.example.filemanager.tests.main

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.filemanager.MainActivity
import com.example.filemanager.scenarios
import com.example.filemanager.screens.FileManagerScreen
import com.example.filemanager.screens.FileManagerScreen.emptyStateText
import com.example.filemanager.screens.FileManagerScreen.titleText
import com.example.filemanager.screens.MainScreen
import com.example.filemanager.utils.FileManager

import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)

class FileManagerScreen : TestCase() {

    @get:Rule

    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun openFileManagerScreen() {
        run {
            step("Проверка открытия второго экрана") {
                MainScreen {
                    goToFilesButton {
                        isDisplayed()
                        click()
                    }
                    emptyStateText {
                        isDisplayed()
                        hasText("Нет файлов")
                    }
                    titleText {
                        isDisplayed()
                        hasText("Файлы")
                    }
                }
            }
        }
    }


    @Test
    fun openThreeDots() {
        before {
            scenarios.openFileManagerScreen()
        }
            .after {
                FileManager.clearAllTestFiles()
            }
            .run {
                step("Проверка открытия трех точек") {
                    FileManagerScreen {
                        threeDots {
                            isDisplayed()
                            click()
                        }

                    }
                }
            }
    }
}









