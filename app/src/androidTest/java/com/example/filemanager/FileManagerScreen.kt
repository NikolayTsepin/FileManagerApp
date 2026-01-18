package com.example.filemanager.screens

import androidx.test.espresso.matcher.ViewMatchers.withId
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import org.hamcrest.Matcher
import android.view.View
import com.example.filemanager.R

object FileManagerScreen : Screen<FileManagerScreen>() {

    // Простые элементы
    val backButton = KButton { withId(R.id.btn_back) }
    val titleText = KTextView { withId(R.id.tv_title) }
    val threeDots = KButton { withId(R.id.btn_menu) }
    val emptyStateText = KTextView { withId(R.id.tv_empty_state) }

    // RecyclerView с itemTypeBuilder
    val filesRecyclerView = KRecyclerView(
        builder = { withId(R.id.recycler_view_files) },
        itemTypeBuilder = {
            // Определяем тип элемента списка
            itemType(::FileItem)
        }
    )
}

// Класс для элемента файла в списке
class FileItem(parent: Matcher<View>) : KRecyclerItem<FileItem>(parent) {
    // Здесь можно добавить элементы внутри item файла
    // Например, если в вашем item_file.xml есть такие элементы:
    val fileName = KTextView { withId(R.id.tv_file_name) }
    val fileSize = KTextView { withId(R.id.tv_file_size) }
    val fileDate = KTextView { withId(R.id.tv_file_date) }
    val moreButton = KButton { withId(R.id.btn_file_more) }
}