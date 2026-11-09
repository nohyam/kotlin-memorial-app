package com.nohyam.memorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nohyam.memorial.ui.navigation.AppNavigation
import com.nohyam.memorial.ui.theme.MemorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemorialTheme {
                AppNavigation()
            }
        }
    }
}
