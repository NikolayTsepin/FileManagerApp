// FilesAdapter.kt
package com.example.filemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilesAdapter(
    private val items: List<FileItem>,
    private val onItemClick: (FileItem) -> Unit
) : RecyclerView.Adapter<FilesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.iv_file_icon)
        val name: TextView = view.findViewById(R.id.tv_file_name)
        val size: TextView = view.findViewById(R.id.tv_file_size)
        val date: TextView = view.findViewById(R.id.tv_file_date)
        val moreButton: ImageView = view.findViewById(R.id.btn_file_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.name.text = item.name
        holder.size.text = item.size
        holder.date.text = item.date

        // Установка иконки в зависимости от типа файла
        if (item.file.isDirectory) {
            holder.icon.setImageResource(R.drawable.ic_folder)
        } else {
            holder.icon.setImageResource(R.drawable.ic_file)
        }

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

        holder.moreButton.setOnClickListener {
            // Показать меню для файла (удалить, переименовать и т.д.)
        }
    }

    override fun getItemCount() = items.size
}