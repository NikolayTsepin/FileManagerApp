package com.example.filemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.databinding.ActivityFileManagerBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FileManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileManagerBinding
    private val items = mutableListOf<FileItem>()
    private lateinit var adapter: FileAdapter

    private val pathStack = mutableListOf<String>().apply { add("root") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
        loadFolderContents("root")
        updateUI()
    }

    private fun setupRecyclerView() {
        adapter = FileAdapter(items) { item ->
            if (item.isFolder) {
                pathStack.add(item.name)
                loadFolderContents(item.name)
                updateUI()
            } else {
                Toast.makeText(this, "Открыт файл: ${item.name}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            if (pathStack.size > 1) {
                pathStack.removeLast()
                loadFolderContents(pathStack.last())
                updateUI()
            } else {
                finish()
            }
        }

        binding.btnMenu.setOnClickListener {
            showOptionsMenu()
        }
    }

    private fun showOptionsMenu() {
        val options = arrayOf("Создать папку", "Добавить файл")

        MaterialAlertDialogBuilder(this)
            .setTitle("Выберите действие")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> createFolder()
                    1 -> addFile()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun createFolder() {
        val folderNumber = items.count { it.isFolder } + 1
        val newFolder = FileItem(
            name = "Папка $folderNumber",
            isFolder = true,
            timestamp = System.currentTimeMillis()
        )

        items.add(newFolder)
        adapter.notifyItemInserted(items.size - 1)
        Toast.makeText(this, "Папка создана", Toast.LENGTH_SHORT).show()
    }

    private fun addFile() {
        val fileNumber = items.count { !it.isFolder } + 1
        val newFile = FileItem(
            name = "Файл $fileNumber.txt",
            isFolder = false,
            timestamp = System.currentTimeMillis()
        )

        items.add(newFile)
        adapter.notifyItemInserted(items.size - 1)
        Toast.makeText(this, "Файл добавлен", Toast.LENGTH_SHORT).show()
    }

    private fun loadFolderContents(folderName: String) {
        items.clear()
        when (folderName) {
            "root" -> {
                items.addAll(listOf(
                    FileItem("Документы", true, System.currentTimeMillis() - 86400000),
                    FileItem("Изображения", true, System.currentTimeMillis() - 172800000),
                    FileItem("Заметка.txt", false, System.currentTimeMillis() - 3600000)
                ))
            }
            "Папка 1" -> {
                items.addAll(listOf(
                    FileItem("Документ 1.pdf", false, System.currentTimeMillis() - 7200000),
                    FileItem("Изображение 1.jpg", false, System.currentTimeMillis() - 3600000)
                ))
            }
            else -> {
                items.add(FileItem("Новая папка", true, System.currentTimeMillis()))
                items.add(FileItem("Новый файл.txt", false, System.currentTimeMillis()))
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun updateUI() {
        binding.tvTitle.text = "Папка: ${pathStack.last()}"
        binding.btnBack.setImageResource(
            if (pathStack.size > 1) android.R.drawable.ic_menu_directions
            else android.R.drawable.ic_media_previous
        )
    }

    data class FileItem(
        val name: String,
        val isFolder: Boolean,
        val timestamp: Long
    )

    class FileAdapter(
        private val items: List<FileItem>,
        private val onItemClick: (FileItem) -> Unit
    ) : RecyclerView.Adapter<FileAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tvName)
            val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
            val icon: TextView = view.findViewById(R.id.icon)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_file, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]

            holder.icon.text = if (item.isFolder) "📁" else "📄"
            holder.tvName.text = item.name

            holder.itemView.setOnClickListener {
                onItemClick(item)
            }

            holder.btnDelete.setOnClickListener {
                Toast.makeText(
                    holder.itemView.context,
                    "Удалено: ${item.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun getItemCount() = items.size
    }
}