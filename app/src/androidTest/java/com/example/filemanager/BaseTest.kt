package com.example.filemanager.base

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.filemanager.MainActivity
import com.example.filemanager.utils.TestFileManager
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java) // ← ActivityScenarioRule с большой A

    protected lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    open fun setUp() {
        scenario = activityRule.scenario
        // ОЧИСТКА перед каждым тестом
        TestFileManager.clearAllTestFiles()
    }

    @After
    open fun tearDown() {
        // Дополнительная очистка если нужно
        scenario.close()
    }

    // Общие методы
    fun waitFor(millis: Long = 1000) {
        Thread.sleep(millis)
    }
}