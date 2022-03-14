package com.khue.joliecafejp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.khue.joliecafejp.presentation.screens.main.MainScreen
import com.khue.joliecafejp.ui.theme.JolieCafeJPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            JolieCafeJPTheme {
                MainScreen()
            }
        }
    }
}

