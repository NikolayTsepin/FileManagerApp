package com.example.filemanager.screens

import androidx.test.espresso.matcher.ViewMatchers.withId
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import io.github.kakaocup.kakao.recycler.KRecyclerView
import com.example.filemanager.R

object FileManagerScreen : Screen<FileManagerScreen>() {

    val titleText = KTextView { withId(R.id.tvTitle) }
    val backButton = KButton { withId(R.id.btnBack) }
    val menuButton = KButton { withId(R.id.btnMenu) }

    }
