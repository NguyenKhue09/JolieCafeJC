package com.khue.joliecafejp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.khue.joliecafejp.ui.theme.JolieCafeJPTheme
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.khue.joliecafejp.screens.LoginScreen
import com.khue.joliecafejp.ui.theme.greyPrimary
import com.khue.joliecafejp.ui.theme.montserratFontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            JolieCafeJPTheme {
                //MainScreen()
                LoginScreen()
            }
        }
    }
}

