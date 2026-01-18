package com.example.filemanager.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileWriter

object FileManager {

    // Тестовая директория в cacheDir приложения
    private val testDir: File by lazy {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        File(context.cacheDir, "test_files").apply {
            if (!exists()) mkdirs()
        }
    }

    // Создать тестовый файл
    fun createTestFile(filename: String, content: String = "test content"): File {
        val file = File(testDir, filename)
        FileWriter(file).use { writer ->
            writer.write(content)
        }
        return file
    }

    // Создать тестовую папку
    fun createTestFolder(folderName: String): File {
        val folder = File(testDir, folderName)
        folder.mkdirs()
        return folder
    }

    // Удалить тестовый файл/папку
    fun deleteTestFile(name: String): Boolean {
        val file = File(testDir, name)
        return if (file.exists()) {
            if (file.isDirectory) {
                file.deleteRecursively()
            } else {
                file.delete()
            }
        } else false
    }

    // Получить список тестовых файлов
    fun listTestFiles(): List<String> {
        return testDir.list()?.toList() ?: emptyList()
    }

    // Очистить ВСЕ тестовые файлы
    fun clearAllTestFiles() {
        if (testDir.exists()) {
            testDir.deleteRecursively()
            testDir.mkdirs()
        }
    }

    // Проверить существует ли файл
    fun fileExists(filename: String): Boolean {
        return File(testDir, filename).exists()
    }

    // Получить путь к тестовой директории
    fun getTestDirPath(): String = testDir.absolutePath

    // Количество файлов в тестовой директории
    fun getFileCount(): Int = testDir.list()?.size ?: 0
}