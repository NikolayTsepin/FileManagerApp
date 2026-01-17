package com.example.filemanager.base

object TestData {
    // Константы для тестов
    const val DEFAULT_WAIT_TIME = 2000L
    const val SHORT_WAIT_TIME = 500L

    // Тестовые данные
    object Files {
        const val TEST_FOLDER_NAME = "Тестовая папка"
        const val TEST_FILE_NAME = "test_file.txt"
        const val IMAGE_FOLDER = "Изображения"
        const val DOCUMENTS_FOLDER = "Документы"
    }

    // Сообщения/тексты для проверок
    object Texts {
        const val WELCOME_TEXT = "Главная страница с тестовым текстом"
        const val GO_TO_FILES_BUTTON = "Перейти к файлам"
        const val ROOT_FOLDER_TEXT = "Папка: root"
    }
}