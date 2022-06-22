package com.khue.joliecafejp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.messaging.FirebaseMessaging
import com.khue.joliecafejp.presentation.screens.main.MainScreen
import com.khue.joliecafejp.ui.theme.JolieCafeJPTheme
import com.khue.joliecafejp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

const val TOPIC = Constants.TOPIC

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeNotificationTopic()
        setContent {
            JolieCafeJPTheme {
                MainScreen()
            }
        }
    }

    private fun subscribeNotificationTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }
}



