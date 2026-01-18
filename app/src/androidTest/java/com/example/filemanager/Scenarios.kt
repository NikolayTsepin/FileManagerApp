package com.example.filemanager

import com.example.filemanager.screens.FileManagerScreen
import com.example.filemanager.screens.MainScreen

object scenarios  {
    fun openFileManagerScreen() {
        MainScreen {
            goToFilesButton {
                isDisplayed()
                isClickable()
                click()
            }
        }

        FileManagerScreen {
            titleText {
                isDisplayed()
                hasText("Файлы")
            }
        }
    }
}
