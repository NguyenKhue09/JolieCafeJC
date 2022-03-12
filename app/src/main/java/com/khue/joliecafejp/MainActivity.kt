package com.khue.joliecafejp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.khue.joliecafejp.ui.theme.JolieCafeJPTheme
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.khue.joliecafejp.ui.theme.grey_primary
import com.khue.joliecafejp.ui.theme.montserratFontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            JolieCafeJPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = grey_primary
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        fontFamily = montserratFontFamily,
        color = Color.White,
        fontSize = 20.sp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JolieCafeJPTheme {
        Greeting("Android")
    }
}