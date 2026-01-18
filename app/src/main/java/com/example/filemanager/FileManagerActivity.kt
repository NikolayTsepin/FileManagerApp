package com.example.filemanager

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FileManagerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilesAdapter
    private val filesList = mutableListOf<FileItem>() // Изначально пустой список
    private val storageDir: File by lazy {
        File(filesDir, "my_files") // Папка для хранения файлов
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_manager)

        // Создаем папку если не существует
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        initViews()
        loadFiles() // Загружаем файлы (изначально будет пусто)
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view_files)
        val emptyState = findViewById<TextView>(R.id.tv_empty_state)
        val menuButton = findViewById<ImageButton>(R.id.btn_menu)

        // ⭐ ДОБАВЛЕНО: находим кнопку назад
        val backButton = findViewById<ImageButton>(R.id.btn_back)

        // ⭐ ДОБАВЛЕНО: обработчик кнопки назад
        backButton.setOnClickListener {
            finish() // Закрываем Activity
        }

        // Настройка RecyclerView
        adapter = FilesAdapter(filesList) { fileItem ->
            // Обработка клика по файлу
            openFile(fileItem.file)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Кнопка меню (три точки)
        menuButton.setOnClickListener {
            showCreateMenu()
        }

        // Показываем/скрываем текст пустого состояния
        updateEmptyState()
    }

    private fun loadFiles() {
        filesList.clear()

        // Получаем список файлов из папки
        val files = storageDir.listFiles() ?: emptyArray()

        files.forEach { file ->
            filesList.add(FileItem(
                name = file.name,
                size = "${file.length()} bytes",
                date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(file.lastModified())),
                file = file
            ))
        }

        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    private fun updateEmptyState() {
        val emptyState = findViewById<TextView>(R.id.tv_empty_state)
        if (filesList.isEmpty()) {
            emptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showCreateMenu() {
        val items = arrayOf("Создать текстовый файл", "Создать папку", "Добавить файл")

        MaterialAlertDialogBuilder(this)
            .setTitle("Создать")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> createTextFile()
                    1 -> createFolder()
                    2 -> addFile()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun createTextFile() {
        val editText = EditText(this)
        editText.hint = "Имя файла"

        MaterialAlertDialogBuilder(this)
            .setTitle("Создать текстовый файл")
            .setView(editText)
            .setPositiveButton("Создать") { _, _ ->
                val fileName = editText.text.toString().trim()
                if (fileName.isNotEmpty()) {
                    val file = File(storageDir, "$fileName.txt")
                    file.writeText("") // Создаем пустой файл
                    loadFiles() // Обновляем список
                    Toast.makeText(this, "Файл создан", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun createFolder() {
        val editText = EditText(this)
        editText.hint = "Имя папки"

        MaterialAlertDialogBuilder(this)
            .setTitle("Создать папку")
            .setView(editText)
            .setPositiveButton("Создать") { _, _ ->
                val folderName = editText.text.toString().trim()
                if (folderName.isNotEmpty()) {
                    val folder = File(storageDir, folderName)
                    folder.mkdirs()
                    loadFiles()
                    Toast.makeText(this, "Папка создана", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun addFile() {
        // Здесь можно реализовать выбор файла из системы
        Toast.makeText(this, "Функция добавления файла", Toast.LENGTH_SHORT).show()
    }

    private fun openFile(file: File) {
        // Открытие файла
        Toast.makeText(this, "Открыт файл: ${file.name}", Toast.LENGTH_SHORT).show()
    }
}

// Модель данных
data class FileItem(
    val name: String,
    val size: String,
    val date: String,
    val file: File
)