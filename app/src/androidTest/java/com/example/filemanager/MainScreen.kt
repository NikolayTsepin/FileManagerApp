package com.example.filemanager.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.filemanager.R
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object MainScreen : Screen<MainScreen>() {

    // Используем классы Kakao, а не Espresso matchers
    val welcomeText = KTextView { withId(R.id.tvWelcome) }
    val goToFilesButton = KButton { withId(R.id.btnGoToFiles) }


    }

